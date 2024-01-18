package com.solvd.schedulegenerator.domain;

import java.util.ArrayList;
import java.util.List;

public class DomainFactory {

    // I'm going to do 1 simple factory class for now,
    // and I can add separate factory classes later if creation logic gets too complex

    public static Course createCourse(long id, long groupId, long subjectId, long timeSlot, String dayOfWeek) {
        return new Course(id, groupId, subjectId, timeSlot, dayOfWeek);
    }

    public static Student createStudent(long id, String firstName, String lastName, long groupId) {
        return new Student(id, firstName, lastName, groupId);
    }

    public static StudentGroup createStudentGroup(long id) {
        return new StudentGroup(id);
    }

    public static Subject createSubject(long id, long roomId, String name) {
        return new Subject(id, roomId, name);
    }

    // Additional methods to create a list of a certain domain object

    public static List<Student> createStudents(List<Long> ids, List<String> firstNames, List<String> lastNames, long groupId) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            students.add(createStudent(ids.get(i), firstNames.get(i), lastNames.get(i), groupId));
        }
        return students;
    }

    public static List<StudentGroup> createStudentGroups(List<Long> ids) {
        List<StudentGroup> studentGroups = new ArrayList<>();
        for (Long id : ids) {
            studentGroups.add(createStudentGroup(id));
        }
        return studentGroups;
    }

    public static List<Subject> createSubjects(List<Long> ids, long roomId, List<String> names) {
        List<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            subjects.add(createSubject(ids.get(i), roomId, names.get(i)));
        }
        return subjects;
    }

    public static List<Course> createCourses(List<Long> ids, long groupId, long subjectId, List<Long> timeSlots, List<String> daysOfWeek) {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            courses.add(createCourse(ids.get(i), groupId, subjectId, timeSlots.get(i), daysOfWeek.get(i)));
        }
        return courses;
    }
}
