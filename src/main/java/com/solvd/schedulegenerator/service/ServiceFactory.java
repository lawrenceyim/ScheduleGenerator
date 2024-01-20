package com.solvd.schedulegenerator.service;


import com.solvd.schedulegenerator.domain.*;
import com.solvd.schedulegenerator.service.BaseService;
import com.solvd.schedulegenerator.service.impl.*;

public class ServiceFactory {

    public BaseService<?> getService(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Service class cannot be null");
        }
        if (clazz.equals(Course.class)) {
            return new CourseServiceImpl();
        } else if (clazz.equals(Student.class)) {
            return new StudentServiceImpl();
        } else if (clazz.equals(StudentGroup.class)) {
            return new StudentGroupServiceImpl();
        } else if (clazz.equals(Subject.class)) {
            return new SubjectServiceImpl();
        } else if (clazz.equals(Teacher.class)) {
            return new TeacherServiceImpl();
        }
        throw new IllegalArgumentException("Unknown service type: " + clazz.getName());
    }
}
