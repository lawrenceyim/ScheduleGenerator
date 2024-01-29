package com.solvd.schedulegenerator.service;

import com.solvd.schedulegenerator.domain.ClassPeriod;

public interface ClassPeriodService extends BaseService<ClassPeriod> {
    void create(ClassPeriod classPeriod);
    void displaySchedule();
}
