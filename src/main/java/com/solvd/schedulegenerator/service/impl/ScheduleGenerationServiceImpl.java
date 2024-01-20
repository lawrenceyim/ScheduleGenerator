package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.List;

public class ScheduleGenerationServiceImpl implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Subject> subjects;
    private List<Course> courses;
    private int coursesPerDay; // No more than 5 per day



    private ScheduleGenerationServiceImpl() {
    }

    public static class Builder {
        private ScheduleGenerationServiceImpl scheduleGenerationService;

        public Builder() {
            this.scheduleGenerationService = new ScheduleGenerationServiceImpl();
        }

        public Builder setGroups(List<StudentGroup> groups) {
            scheduleGenerationService.groups = groups;
            return this;
        }

        public Builder setSubjects(List<Subject> subjects) {
            scheduleGenerationService.subjects = subjects;
            return this;
        }

        public Builder setCourses(List<Course> courses) {
            scheduleGenerationService.courses = courses;
            return this;
        }

        public Builder setCoursesPerDay(int coursesPerDay) {
            scheduleGenerationService.coursesPerDay = coursesPerDay;
            return this;
        }

        public ScheduleGenerationService build() {
            return scheduleGenerationService;
        }
    }

    @Override
    public List<Course> generateSchedule() {
        return null;
    }
}
