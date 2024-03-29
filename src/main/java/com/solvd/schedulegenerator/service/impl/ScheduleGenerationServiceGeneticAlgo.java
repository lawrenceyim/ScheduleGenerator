package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.domain.Room;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.domain.Teacher;
import com.solvd.schedulegenerator.persistence.ClassPeriodDao;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.utils.MyBatisSessionFactory;
import com.solvd.schedulegenerator.utils.Pair;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ScheduleGenerationServiceGeneticAlgo implements ScheduleGenerationService {
    // Domain models
    private List<Teacher> teachers;
    private List<Room> rooms;
    private List<StudentGroup> groups;
    private List<Subject> subjects;  // All subjects
    private List<Long> subjectConstraints;
    private List<Subject> subjectsWithoutConstraint;
    private List<List<ClassPeriod>> population;

    private HashMap<Long, Long> subjectTeacherMap; // Edge cases for 1 teacher per one subject

    // GA Attributes and Constraints
    private int coursesPerDay;  // No more than 5 per day
    private final int DAYS_PER_WEEK = 5;
    private int populationSize = 100;
    private HashMap<Long, Subject> subjectIdMap;

    // Tournament Selection
    private final int TOURNAMENT_SIZE = 3; // Size of each tournament
    private final double MUTATION_RATE = 0.05;
    private Random random = new Random();

    // Constructor
    public ScheduleGenerationServiceGeneticAlgo() {
        this.subjectIdMap = generateSubjectIdMap();
    }

    // Builder class
    public static class Builder {
        private final ScheduleGenerationServiceGeneticAlgo service;

        public Builder() {
            this.service = new ScheduleGenerationServiceGeneticAlgo();
            this.service.subjectTeacherMap = new HashMap<>();
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setGroups(List<StudentGroup> groups) {
            service.groups = groups;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setSubjects(List<Subject> subjects) {
            service.subjects = subjects;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setTeachers(List<Teacher> teachers) {
            service.teachers = teachers;
            teachers.forEach(teacher -> service.subjectTeacherMap.put(teacher.getSubjectId(), teacher.getId()));
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setRooms(List<Room> rooms) {
            service.rooms = rooms;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setCoursesPerDay(int coursesPerDay) {
            service.coursesPerDay = coursesPerDay;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setCoursesWithConstraints(List<Long> subjectConstraints) {
            service.subjectConstraints = subjectConstraints;
            return this;
        }

        public ScheduleGenerationService build() {
            service.initializeAttributes();
            return service;
        }
    }

    // Initialize additional attributes based on the set values
    private void initializeAttributes() {
        this.subjectIdMap = generateSubjectIdMap();
        this.subjectsWithoutConstraint = getSubjectsWithoutConstraints();
        this.population = new ArrayList<>();
    }

    private List<Subject> getSubjectsWithoutConstraints() {
        return subjects.stream()
                .filter(subject -> !subjectConstraints.contains(subject.getId()))
                .collect(Collectors.toList());
    }

    // Generate a mapping from subject ID to Subject
    private HashMap<Long, Subject> generateSubjectIdMap() {
        HashMap<Long, Subject> hashMap = new HashMap<>();
        if (subjects != null) {
            subjects.forEach(subject -> hashMap.put(subject.getId(), subject));
        }
        return hashMap;
    }

    // Other methods for the class
    @Override
    public List<ClassPeriod> generateSchedule() {
        initializePopulation();
        for (int generation = 0; generation < 1000; generation++) {
            List<List<ClassPeriod>> parents = selectParents(this.population);
            List<List<ClassPeriod>> offsprings = crossoverAndMutate(parents);
            this.population = offsprings;
        }
        List<ClassPeriod> bestSchedule = Collections.max(population, Comparator.comparingInt(this::evaluateFitness));
        return bestSchedule;
    }

    @Override
    public void generateAndSaveSchedule() {
        List<ClassPeriod> schedule = generateSchedule();
        try (SqlSession sqlSession = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ClassPeriodDao classPeriodDao = sqlSession.getMapper(ClassPeriodDao.class);
            schedule.stream().forEach(classPeriod -> {
                classPeriodDao.create(classPeriod.getTeacherId(),
                        classPeriod.getRoomId(),
                        classPeriod.getGroupId(),
                        classPeriod.getSubjectId(),
                        classPeriod.getTimeslot());
            });
            sqlSession.commit();
        }
    }

    // Initializes the population for the genetic algorithm
    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomSchedule());
        }
    }

    private List<ClassPeriod> generateRandomSchedule() {
        List<ClassPeriod> schedule = new ArrayList<>();

        for (StudentGroup group : groups) {
            for (int day = 0; day < DAYS_PER_WEEK; day++) {
                for (int course = 0; course < coursesPerDay; course++) {
                    long subjectId;
                    if (course == coursesPerDay - 1) {
                        subjectId = subjects.get(random.nextInt(subjects.size())).getId();
                    } else {
                        subjectId = subjectsWithoutConstraint.get(random.nextInt
                                (subjectsWithoutConstraint.size())).getId();
                    }

                    Long teacherId = subjectTeacherMap.get(subjectId);
                    if (teacherId == null) {
                        // Edge case for 1 teacher 1 subject
                        continue;
                    }

                    long roomId = rooms.get(random.nextInt(rooms.size())).getId();
                    int timeslot = day * coursesPerDay + course;
                    schedule.add(new ClassPeriod(teacherId, roomId, group.getId(), subjectId, timeslot));
                }
            }
        }
        moveSubjectToEnd(schedule);
        return schedule;
    }

    private void moveSubjectToEnd(List<ClassPeriod> schedule) {
        for (ClassPeriod period : schedule) {
            if (isLastPeriodOfDay(period.getTimeslot())) {
                Subject subject = subjectIdMap.get(period.getSubjectId());
                if (subject != null && subject.getShouldBeLast()) {
                    int newTimeslot = findEndOfDayTimeslot(schedule, period.getGroupId());
                    if (newTimeslot != -1) {
                        period.setTimeslot(newTimeslot);
                    }
                }
            }
        }
    }

    private int findEndOfDayTimeslot(List<ClassPeriod> schedule, long groupId) {
        Set<Integer> occupiedEndTimeslots = new HashSet<>();

        for (ClassPeriod period : schedule) {
            int day = period.getTimeslot() / coursesPerDay;
            int timeslot = period.getTimeslot() % coursesPerDay;

            if (timeslot == coursesPerDay - 1 && period.getGroupId() == groupId) {
                occupiedEndTimeslots.add(day * coursesPerDay + timeslot);
            }
        }

        for (int day = 0; day < DAYS_PER_WEEK; day++) {
            int endOfDayTimeslot = day * coursesPerDay + coursesPerDay - 1;
            if (!occupiedEndTimeslots.contains(endOfDayTimeslot)) {
                return endOfDayTimeslot;
            }
        }

        return -1; // Can't find a timeslot, just have to change add more table entries
    }

    private long findSubjectForLastPeriod() {
        return subjects.stream()
                .filter(Subject::getShouldBeLast)
                .findAny()
                .orElseThrow(() -> new RuntimeException("No subject found for last period"))
                .getId();
    }

    private int evaluateFitness(List<ClassPeriod> schedule) {
        int fitness = 1000; // Starting fitness value

        Map<Pair<Integer, Integer>, Long> teacherTimeslotMap = new HashMap<>();
        Map<Pair<Integer, Integer>, Long> roomTimeslotMap = new HashMap<>();
        Map<Long, List<Pair<Integer, Integer>>> groupScheduleMap = new HashMap<>();

        for (ClassPeriod period : schedule) {
            int day = period.getTimeslot() / coursesPerDay;
            int timeslot = period.getTimeslot() % coursesPerDay;
            Pair<Integer, Integer> timeslotKey = new Pair<>(day, timeslot);

            // Teacher overlap check
            if (teacherTimeslotMap.containsKey(timeslotKey) &&
                    teacherTimeslotMap.get(timeslotKey).equals(period.getTeacherId())) {
                fitness -= 10;
            } else {
                teacherTimeslotMap.put(timeslotKey, period.getTeacherId());
            }

            // Room availability check
            if (roomTimeslotMap.containsKey(timeslotKey) &&
                    roomTimeslotMap.get(timeslotKey).equals(period.getRoomId())) {
                fitness -= 10;
            } else {
                roomTimeslotMap.put(timeslotKey, period.getRoomId());
            }

            groupScheduleMap.computeIfAbsent(period.getGroupId(), k -> new ArrayList<>()).add(timeslotKey);
        }

        // Check for no gaps in schedule and some lessons should be the last in the day
        for (Map.Entry<Long, List<Pair<Integer, Integer>>> entry : groupScheduleMap.entrySet()) {
            List<Pair<Integer, Integer>> groupTimeslots = entry.getValue();
            Collections.sort(groupTimeslots, Comparator.comparingInt(Pair::getFirst));

            // Check for more than 5 lessons a day or gaps in the schedule
            int[] dailyLessonsCount = new int[DAYS_PER_WEEK];
            Pair<Integer, Integer> prevTimeslot = null;
            for (Pair<Integer, Integer> timeslot : groupTimeslots) {
                if (prevTimeslot != null &&
                        (timeslot.getFirst() != prevTimeslot.getFirst() || timeslot.getSecond() != prevTimeslot.getSecond() + 1)) {
                    fitness -= 5; // Penalty for gaps
                }
                dailyLessonsCount[timeslot.getFirst()]++;
                prevTimeslot = timeslot;
            }

            // Check for daily lessons limit and lessons at the end of the day
            for (int day = 0; day < DAYS_PER_WEEK; day++) {
                int lastPeriodIndex = day * coursesPerDay + coursesPerDay - 1;
                for (ClassPeriod period : schedule) {
                    if (period.getTimeslot() == lastPeriodIndex) {
                        Subject subject = subjectIdMap.get(period.getSubjectId());
                        if (subject != null && subject.getShouldBeLast()) {
                            fitness += 10; // Increase fitness if condition met
                        } else if (subject != null && !subject.getShouldBeLast()) {
                            fitness -= 5; // Penalty if a non-last subject is placed at the end of the day
                        }
                    }
                }
            }
        }
        return fitness;
    }

    private boolean isLastPeriodOfDay(int timeslot) {
        return timeslot % coursesPerDay == coursesPerDay - 1;
    }

    private List<List<ClassPeriod>> selectParents(List<List<ClassPeriod>> population) {
        List<List<ClassPeriod>> parents = new ArrayList<>();

        while (parents.size() < population.size()) {
            List<ClassPeriod> parent1 = tournamentSelection(population);
            List<ClassPeriod> parent2 = tournamentSelection(population);

            // Ensure different parents are selected
            while (parent1.equals(parent2)) {
                parent2 = tournamentSelection(population);
            }

            parents.add(parent1);
            parents.add(parent2);
        }
        return parents;
    }

    private List<ClassPeriod> tournamentSelection(List<List<ClassPeriod>> population) {
        List<ClassPeriod> best = null;
        int bestFitness = Integer.MIN_VALUE;

        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndex = random.nextInt(population.size());
            List<ClassPeriod> individual = population.get(randomIndex);
            int individualFitness = evaluateFitness(individual);

            if (best == null || individualFitness > bestFitness) {
                best = individual;
                bestFitness = individualFitness;
            }
        }

        return best;
    }

    // Tournament Selection
    private List<List<ClassPeriod>> crossover(List<ClassPeriod> parent1, List<ClassPeriod> parent2) {
        int crossoverPoint = random.nextInt(parent1.size()); // Choose a random point for crossover
        List<List<ClassPeriod>> offsprings = new ArrayList<>();

        // Creating two new offspring from the parent schedules
        List<ClassPeriod> offspring1 = new ArrayList<>(parent1.subList(0, crossoverPoint));
        offspring1.addAll(parent2.subList(crossoverPoint, parent2.size()));

        List<ClassPeriod> offspring2 = new ArrayList<>(parent2.subList(0, crossoverPoint));
        offspring2.addAll(parent1.subList(crossoverPoint, parent1.size()));

        offsprings.add(offspring1);
        offsprings.add(offspring2);

        return offsprings;
    }

    private void mutate(List<ClassPeriod> schedule) {
        for (int i = 0; i < schedule.size(); i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                ClassPeriod period = schedule.get(i);

                mutateTeacher(period);
                mutateSubject(period);
                mutateRoom(period);
            }
        }
        moveSubjectToEnd(schedule);

    }

    // Mutate helper methods
    private void mutateTeacher(ClassPeriod period) {
        // Choose a random teacher
        long newTeacherId = teachers.get(random.nextInt(teachers.size())).getId();
        period.setTeacherId(newTeacherId);
    }

    private void mutateSubject(ClassPeriod period) {
        // Choose a random subject, but also check if the teacher can teach it
        long newSubjectId = subjects.get(random.nextInt(subjects.size())).getId();
        if (subjectTeacherMap.containsKey(newSubjectId)) {
            period.setSubjectId(newSubjectId);
            period.setTeacherId(subjectTeacherMap.get(newSubjectId));
        }
    }

    private void mutateRoom(ClassPeriod period) {
        // Choose a random room
        long newRoomId = rooms.get(random.nextInt(rooms.size())).getId();
        period.setRoomId(newRoomId);
    }

    private List<List<ClassPeriod>> crossoverAndMutate(List<List<ClassPeriod>> parents) {
        List<List<ClassPeriod>> offsprings = new ArrayList<>();
        for (int i = 0; i < parents.size(); i += 2) {
            offsprings.addAll(crossover(parents.get(i), parents.get(i + 1)));
        }
        offsprings.forEach(this::mutate);
        return offsprings;
    }
}

