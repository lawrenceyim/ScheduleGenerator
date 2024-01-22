package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.Teacher;
import com.solvd.schedulegenerator.service.BaseService;
import com.solvd.schedulegenerator.service.SubjectService;
import com.solvd.schedulegenerator.service.TeacherService;
import com.solvd.schedulegenerator.service.impl.*;

import java.util.List;

public class App {

    private static BaseService service = new CourseServiceImpl();

    public static void main(String[] args){
        System.out.println(service.findById(2));
        System.out.println();
        service.findAll().stream().forEach(System.out::println);
    }
}
