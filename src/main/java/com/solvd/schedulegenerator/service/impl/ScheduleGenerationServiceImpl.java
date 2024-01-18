package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.Student;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.List;

public class ScheduleGenerationServiceImpl implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Course> courses;

    @Override
    public List<Course> generateSchedule() {
        return null;
    }
}
