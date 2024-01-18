package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.Teacher;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.service.impl.SubjectServiceImpl;
import com.solvd.schedulegenerator.service.impl.TeacherServiceImpl;

import java.util.List;

public class App {

    private static TeacherService teacherService = new TeacherServiceImpl();
    private static SubjectService subjectService = new SubjectServiceImpl();

    public static void main(String[] args){
        System.out.println(subjectService.findById(1));
        System.out.println(subjectService.findById(2));
        System.out.println();
        subjectService.findAll().stream().forEach(System.out::println);
    }
}
