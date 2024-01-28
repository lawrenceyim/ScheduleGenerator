package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.ClassPeriod;

import java.util.List;

public interface ScheduleGenerationService {
    List<ClassPeriod> generateSchedule();
}
