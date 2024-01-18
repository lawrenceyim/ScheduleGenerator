package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.DomainFactory;
import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Verifying that Factory Pattern works

        Student student1 = DomainFactory.createStudent(1L, "John", "Doe", 101L);

        List<Long> studentIds = Arrays.asList(1L, 2L, 3L);
        List<String> firstNames = Arrays.asList("John", "Jane", "Jim");
        List<String> lastNames = Arrays.asList("Doe", "Smith", "Bean");
        List<Student> students = DomainFactory.createStudents(studentIds, firstNames, lastNames, 101L);

        StudentGroup group1 = DomainFactory.createStudentGroup(101L);

        Subject subject1 = DomainFactory.createSubject(201L, 301L, "Mathematics");

        Course course1 = DomainFactory.createCourse(401L, group1.getId(), subject1.getId(), 1L, "Monday");

        // Print factory objects
        System.out.println("Created Student: " + student1.getFirstName() + " " + student1.getLastName());
        System.out.println("Created Group ID: " + group1.getId());
        System.out.println("Created Subject: " + subject1.getName());
        System.out.println("Created Course: " + course1.getDayOfWeek());

    }
}
