package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ScheduleGenerationServiceImpl implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Subject> subjects;
    private List<Course> coursesWithConstraint;
    private int coursesPerDay;          // No more than 5 per day
    private final int DAYS_PER_WEEK = 5;
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

        public Builder setCoursesWithConstraints(List<Course> coursesWithConstraints) {
            scheduleGenerationService.coursesWithConstraint = coursesWithConstraints;
            return this;
        }

        public Builder setCoursesPerDay(int coursesPerDay) {
            scheduleGenerationService.coursesPerDay = coursesPerDay;
            scheduleGenerationService.schedule = new Subject
                    [scheduleGenerationService.groups.size()]
                    [scheduleGenerationService.DAYS_PER_WEEK]
                    [scheduleGenerationService.coursesPerDay];
            scheduleGenerationService.totalCourses =
                    scheduleGenerationService.coursesPerDay *
                            scheduleGenerationService.DAYS_PER_WEEK;
            return this;
        }

        public ScheduleGenerationService build() {
            return scheduleGenerationService;
        }
    }

    @Override
    public List<Course> generateSchedule() {
        if (depthFirstSearch(0)) {
            return convertScheduleToCourses();
        } else {
            throw new RuntimeException("Valid schedule could not be formed.");
        }
    }

    private boolean depthFirstSearch(int group) {
        // Base case
        if (coursesAdded == totalCourses) {
            return true;
        }

        for (int day = 0; day < DAYS_PER_WEEK; day++) {
            for (int timeslot = 0; timeslot < coursesPerDay; timeslot++) {
                if (schedule[group][day][timeslot] == null) {
                    schedule[group][day][timeslot] = subjects.get(subjectIterator);
                    if (hasNoConflict(group, day, timeslot)) {
                        group++;
                        if (group == groups.size()) {
                            coursesAdded++;
                            group = 0;
                            subjectIterator = (subjectIterator + 1) % subjects.size();
                        }
                        // If solution is invalid, back track
                        if (!depthFirstSearch(group)) {
                            subjectIterator--;
                            if (subjectIterator < 0) {
                                subjectIterator = subjects.size() - 1;
                            }
                            group--;
                            if (group < 0) {
                                group = groups.size() - 1;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // TODO: Add logic to check for room repetition instead of just class
    private boolean hasNoConflict(int group, int day, int timeslot) {
        for (int i = 0; i < group; i++) {
            if (schedule[i][day][timeslot] == schedule[group][day][timeslot]) {
                schedule[group][day][timeslot] = null;
                return false;
            }
        }
        return true;
    }

    private List<Course> convertScheduleToCourses() {
        List<Course> courses = new ArrayList<>();
        IntStream.range(0, groups.size()).forEach(group -> {
            IntStream.range(0, DAYS_PER_WEEK).forEach(day -> {
                IntStream.range(0, coursesPerDay).forEach(timeslot -> {
                    Course course = new Course();
                    course.setGroupId(groups.get(group).getId());
                    course.setSubjectId(schedule[group][day][timeslot].getId());
                    course.setTimeSlot(timeslot);
                    course.setDayOfWeek(getNameOfDay(day));
                    courses.add(course);
                });
            });
        });
        return courses;
    }

    private String getNameOfDay(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            default:
                throw new RuntimeException("Invalid day of the week.");
        }
    }
}
