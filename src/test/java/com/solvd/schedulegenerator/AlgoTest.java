package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.*;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;
import com.solvd.schedulegenerator.service.impl.ScheduleGenerationServiceGeneticAlgo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

public class AlgoTest {
    private final Logger OUTPUT_LOGGER = (Logger) LogManager.getLogger("Output");

    private Map<Long, Subject> subjectIdMap;

    @Test
    public void generateAndPrintResult() {
        // Instantiate input
        final List<StudentGroup> groups = generateGroups();
        final List<Subject> subjects = generateSubjects();
        final List<Teacher> teachers = generateTeachers();
        final List<Room> rooms = generateRooms();

        ScheduleGenerationService service = null;



        final int coursesPerDay = 5;
        subjectIdMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectIdMap.put(subject.getId(), subject);
        }

        service = new ScheduleGenerationServiceGeneticAlgo.Builder()
                .setGroups(groups)
                .setSubjects(subjects)
                .setTeachers(teachers)
                .setRooms(rooms)
                .setCoursesPerDay(coursesPerDay)
                .build();

        // Run Algo
        List<ClassPeriod> schedule = service.generateSchedule();

        for (ClassPeriod classPeriod : schedule) {
            classPeriod.setStudents(generateStudents()); // Assuming generateStudents() returns a list of students
        }

        // Print result
        printSchedule(schedule);
        validateSchedule(schedule);
    }

    // Test data

    private List<StudentGroup> generateGroups() {
        List<StudentGroup> groups = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            StudentGroup group = new StudentGroup();
            group.setId(i);
            groups.add(group);
        }
        return groups;
    }


    private List<Subject> generateSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String[] subjectNames = {"Physics", "Chemistry", "Calculus", "History", "PE"};
        for (int i = 0; i < subjectNames.length; i++) {
            Subject subject = new Subject();
            subject.setId(i + 1);
            subject.setName(subjectNames[i]);
            subject.setShouldBeLast("PE".equals(subjectNames[i]));
            subjects.add(subject);
        }
        return subjects;
    }


    private List<Teacher> generateTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String[] subjectNames = {"Physics", "Chemistry", "Calculus", "History", "PE"};
        for (int i = 0; i < subjectNames.length; i++) {
            Teacher teacher = new Teacher();
            teacher.setId(i + 1);
            teacher.setFirstName("Teacher");
            teacher.setLastName(String.valueOf(i + 1));
            teacher.setSubjectId(i + 1);
            teachers.add(teacher);
        }
        return teachers;
    }

    private List<Room> generateRooms() {
        List<Room> rooms = new ArrayList<>();
        Object[][] roomData = {
                {"Harrington Hall", 1, 100},
                {"Harrington Hall", 2, 210},
                {"Welsh Building", 1, 150},
                {"Welsh Building", 1, 180}
        };

        for (int i = 0; i < roomData.length; i++) {
            Room room = new Room();
            room.setId(i + 1);
            room.setBuilding((String) roomData[i][0]);
            room.setFloor((Integer) roomData[i][1]);
            room.setRoomNumber((Integer) roomData[i][2]);
            rooms.add(room);
        }
        return rooms;
    }


    private List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Student student = new Student();
            student.setId(i);
            student.setFirstName("Student");
            student.setLastName(String.valueOf(i));
            students.add(student);
        }
        return students;
    }


    private List<Subject> generateSubjectsWithConstraints() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(11, "Physical Education"));
        return subjects;
    }




    public void printSchedule(List<ClassPeriod> schedule) {
        for (ClassPeriod classPeriod : schedule) {
            System.out.println(classPeriod.toString());
        }
    }

    public void validateSchedule(List<ClassPeriod> schedule) {
        Map<Long, Set<Long>> groupToSubjectsMap = new HashMap<>();
        Map<Long, Long> subjectToTeacherMap = new HashMap<>();
        Map<Long, Long> groupToStudentCountMap = new HashMap<>();
        Map<Long, Subject> groupToLastSubjectMap = new HashMap<>();

        for (ClassPeriod classPeriod : schedule) {
            long groupId = classPeriod.getGroupId();
            long subjectId = classPeriod.getSubjectId();
            long teacherId = classPeriod.getTeacherId();

            // Count the number of unique subjects for each group
            groupToSubjectsMap.computeIfAbsent(groupId, k -> new HashSet<>()).add(subjectId);



            // Check that each subject is taught by exactly one teacher
            Long existingTeacherId = subjectToTeacherMap.putIfAbsent(subjectId, teacherId);
            if (existingTeacherId != null && existingTeacherId != teacherId) {
                throw new RuntimeException("Subject " + subjectId + " is taught by more than one teacher");
            }

            // Count the number of students in each group
            // This assumes that the ClassPeriod class has a getStudents method that returns a list of students
            long studentCount = classPeriod.getStudents().size();
            groupToStudentCountMap.put(groupId, studentCount);

            // Check that some lessons are the last in the day
            // This assumes that the ClassPeriod class has a getTimeslot method that returns the timeslot
            long timeslot = classPeriod.getTimeslot();
            int coursesPerDay = 5;
            if (timeslot == coursesPerDay) {
                Subject subject = subjectIdMap.get(subjectId);
                groupToLastSubjectMap.put(groupId, subject);
            }
        }

        // Validate the requirements
        for (long groupId : groupToSubjectsMap.keySet()) {
            if (groupToSubjectsMap.get(groupId).size() != 15) {
                throw new RuntimeException("Group " + groupId + " does not have exactly 15 subjects");
            }
            if (groupToStudentCountMap.get(groupId) != 20) {
                throw new RuntimeException("Group " + groupId + " does not have exactly 20 students");
            }
            if (!groupToLastSubjectMap.get(groupId).getShouldBeLast()) {
                throw new RuntimeException("The last subject of group " + groupId + " should be last");
            }
        }
    }


}
