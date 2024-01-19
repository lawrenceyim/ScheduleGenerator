package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerationServiceImpl implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Subject> subjects;
    private List<Course> courses;
    private int coursesPerDay;          // No more than 5 per day
    private int schoolDaysPerWeek = 5;  // 5 days is default
    private Subject[][][] schedule;     // [group][day of the week][timeslot]
    private int subjectIterator = 0;
    private int coursesAdded = 0;
    private int totalCourses;

    private ScheduleGenerationServiceImpl() {
    }

    public static class Builder {
        private final ScheduleGenerationServiceImpl scheduleGenerationService;

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

        public Builder setSchoolDaysPerWeek(int schoolDaysPerWeek) {
            scheduleGenerationService.schoolDaysPerWeek = schoolDaysPerWeek;
            return this;
        }

        public Builder setCoursesPerDay(int coursesPerDay) {
            scheduleGenerationService.coursesPerDay = coursesPerDay;
            scheduleGenerationService.schedule = new Subject
                    [scheduleGenerationService.groups.size()]
                    [scheduleGenerationService.schoolDaysPerWeek]
                    [scheduleGenerationService.coursesPerDay];
            scheduleGenerationService.totalCourses =
                    scheduleGenerationService.coursesPerDay *
                            scheduleGenerationService.schoolDaysPerWeek;
            return this;
        }

        public ScheduleGenerationService build() {
            return scheduleGenerationService;
        }
    }


    // Add values with constraint first
    // Then add values with no constraints
    // After list of values is exhausted, restart from beginning of the list
    @Override
    public List<Course> generateSchedule() {
        while (coursesAdded < totalCourses) {
            for (int i = 0; i < groups.size(); i++) {
                boolean courseAdded = false;
                for (int j = 0; j < schoolDaysPerWeek; j++) {
                    for (int k = 0; k < coursesPerDay; k++) {
                        if (schedule[i][j][k] == null) {
                            schedule[i][j][k] = subjects.get(subjectIterator);
                            if (!hasNoConflict(i, j, k)) {
                                courseAdded = true;
                                break;
                            } else {
                                schedule[i][j][k] = null;
                            }
                        }
                    }
                    if (courseAdded) {
                        break;
                    }
                }
            }
            subjectIterator = (subjectIterator + 1) % courses.size();
            coursesAdded++;
        }
        return null;
    }

    public boolean hasNoConflict(int group, int day, int timeslot) {
        for (int i = 0; i < group; i++) {
            if (schedule[i][day][timeslot] == schedule[group][day][timeslot]) {
                schedule[group][day][timeslot] = null;
                return false;
            }
        }
        return true;
    }

    public List<Course> convertScheduleToCourses() {
        List<Course> courses = new ArrayList<>();
        // TODO
        return courses;
    }

    // Testing purposes only
    public static void main(String[] args) {
        ScheduleGenerationService s = new ScheduleGenerationServiceImpl.Builder()

                .build();
    }
}
