package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.ClassPeriod;
import com.solvd.schedulegenerator.domain.Course;

import java.util.List;

public interface ScheduleGenerationService {
    List<ClassPeriod> generateSchedule();
}
