package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ScheduleGenerationServiceGeneticAlgo implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Subject> subjects;  // All subjects
    private List<Subject> subjectsWithConstraint;  // Subjects with constraints
    private int coursesPerDay;  // No more than 5 per day
    private final int DAYS_PER_WEEK = 5;
    private List<Long> subjectsToAdd;  // Iterate over this linked hashset to add courses to schedule
    private int totalCourses;
    private int numberOfRooms;
    private int[] coursesAddedToGroup;
    private HashMap<Long, Subject> subjectIdMap;
    private HashSet<List<Long>> tabuList; // Similar to Tabu search, maintain a list of previously seen solutions
    // Each element represents timeslot. Grouped by student group and day. Use % to find specific class
    private List<List<Long>> schedules;

    private ScheduleGenerationServiceGeneticAlgo() {
    }

    public static class Builder {
        private final ScheduleGenerationServiceGeneticAlgo service;

        public Builder() {
            this.service = new ScheduleGenerationServiceGeneticAlgo();
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setGroups(List<StudentGroup> groups) {
            service.groups = groups;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setSubjects(List<Subject> subjects) {
            service.subjects = subjects;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setSubjectsWithConstraints(List<Subject> subjectsWithConstraint) {
            service.subjectsWithConstraint = subjectsWithConstraint;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setCoursesPerDay(int coursesPerDay) {
            service.coursesPerDay = coursesPerDay;
            service.totalCourses = service.coursesPerDay * service.DAYS_PER_WEEK;
            service.numberOfRooms = service.subjects.size();
            service.subjectsToAdd = sortSubjectsByPriority();
            service.coursesAddedToGroup = new int[service.groups.size()];
            service.subjectIdMap = generateSubjectIdMap();
            service.tabuList = new HashSet<>();
            service.schedules = new ArrayList<>();
            return this;
        }

        public ScheduleGenerationService build() {
            return service;
        }

        // Class with constraints go first. Then classes without constraints
        private List<Long> sortSubjectsByPriority() {
            LinkedHashSet<Long> orderedSubjects = new LinkedHashSet<>();
            service.subjectsWithConstraint.stream().forEach(subject -> {
                orderedSubjects.add(subject.getId());
            });
            service.subjects.stream().forEach(subject -> {
                orderedSubjects.add(subject.getId());
            });
            return new ArrayList<>(orderedSubjects);
        }

        private HashMap<Long, Subject> generateSubjectIdMap() {
            HashMap<Long, Subject> hashMap = new HashMap<>();
            service.subjects.stream().forEach(subject -> {
                hashMap.put(subject.getId(), subject);
            });
            return hashMap;
        }
    }

    @Override
    public List<Course> generateSchedule() {
        IntStream.range(0, 1000).forEach(i -> {
            // TODO: Initialize population
            // TODO: Evaluate fitness
            // TODO: Select parents
            // TODO: Create offsprings (recombine)
            // TODO: Create mutations
            // TODO: Create new population
        });
        // TODO: Find and return schedule with the highest fittness score
        return null;
    }

    // Return the schedule index based on the group and timeslot
    private int findIndexByGroupAndTimeslot(int group, int timeslot) {
        return group * subjects.size() + timeslot;
    }

    private Long findSubjectByGroupAndTimeslot(int group, int timeslot, int index) {
        return schedules.get(index).get(findIndexByGroupAndTimeslot(group, timeslot));
    }

    private void setSubjectByGroupAndTimeslot(int group, int timeslot, Long subjectId, int index) {
        List<Long> schedule = schedules.get(index);
        schedule.set(findIndexByGroupAndTimeslot(group, index), subjectId);
        schedules.set(index, schedule);
    }

    private List<Subject> convertIdToSubject(List<Long> schedule) {
        List<Subject> list = new LinkedList<>();
        schedule.forEach(id -> {
            list.add(subjectIdMap.get(id));
        });
        return list;
    }

    private void addToTabuList(Long[] state) {
        tabuList.add(Arrays.asList(state));
    }

    private boolean isInTabuList(Long[] state) {
        return tabuList.contains(state);
    }

    // TODO: Determine population size
    private void generateRandomSchedules() {
        IntStream.range(0, 1000).forEach(i -> {
            schedules.add(generateRandomSchedule());
        });
    }

    private List<Long> generateRandomSchedule() {
        ArrayList<Long> schedule = new ArrayList<>(totalCourses * groups.size());
        // TODO: Implement code to generate random schedules
        return schedule;
    }

    // TODO: Add code to evaluate fitness of individuals in the population
    private int evaluateFitness(int index) {
        int fitness = 0;
        return fitness;
    }

    // TODO: Add code to select pairs of elements in the population to serve as parents for new offsprings
    private List<Pair<Integer, Integer>> selection() {
        ArrayList<Pair<Integer, Integer>> parents = new ArrayList<>();
        return parents;
    }

    // TODO: Add code to recombine parents into offsprings
    private List<List<Long>> recombination(List<Pair<Integer, Integer>> parents) {
        List<List<Long>> offsprings = new ArrayList<>();
        return offsprings;
    }

    // TODO: Add code to mutate individuals in the population
    private List<List<Long>> mutate() {
        List<List<Long>> mutations = new ArrayList<>();
        return mutations;
    }

    // TODO: Add code to create a new population for the next generation
    private void replace(List<List<Long>> parents, List<List<Long>> mutations) {

    }
}
