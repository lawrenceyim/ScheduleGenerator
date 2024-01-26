package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ScheduleGenerationServiceImpl implements ScheduleGenerationService {
    private List<StudentGroup> groups;
    private List<Subject> subjects;  // All subjects
    private List<Subject> subjectsWithConstraint;  // Subjects with constraints
    private int coursesPerDay;  // No more than 5 per day
    private final int DAYS_PER_WEEK = 5;
    private Subject[][][] schedule;  // [group][day of the week][timeslot]
    private List<Subject> subjectsToAdd;  // Iterate over this linked hashset to add courses to schedule
    private int subjectIterator = 0;
    private int coursesAdded = 0;
    private int totalCourses;
    private int numberOfRooms;
    private HashMap<Subject, List<Long>> subjectRoomList; // Store the list of classrooms for a subject

    private ScheduleGenerationServiceImpl() {
    }

    public static class Builder {
        private final ScheduleGenerationServiceImpl service;

        public Builder() {
            this.service = new ScheduleGenerationServiceImpl();
        }

        public Builder setGroups(List<StudentGroup> groups) {
            service.groups = groups;
            return this;
        }

        public Builder setSubjects(List<Subject> subjects) {
            service.subjects = subjects;
            return this;
        }

        public Builder setSubjectsWithConstraints(List<Subject> subjectsWithConstraint) {
            service.subjectsWithConstraint = subjectsWithConstraint;
            return this;
        }

        public Builder setCoursesPerDay(int coursesPerDay) {
            service.coursesPerDay = coursesPerDay;
            service.schedule = new Subject[service.groups.size()][service.DAYS_PER_WEEK][service.coursesPerDay];
            service.numberOfRooms = service.groups.size();
            service.totalCourses = service.coursesPerDay * service.DAYS_PER_WEEK;
            service.subjectsToAdd = sortSubjectsByPriority();
            service.subjectRoomList = generateClassroomListForSubjects();
            return this;
        }

        public ScheduleGenerationService build() {
            return service;
        }

        // Class with constraints go first. Then classes without constraints
        private List<Subject> sortSubjectsByPriority() {
            LinkedHashSet<Subject> orderedSubjects = new LinkedHashSet<>();
            service.subjectsWithConstraint.stream().forEach(subject -> {
                orderedSubjects.add(subject);
            });
            service.subjects.stream().forEach(subject -> {
                orderedSubjects.add(subject);
            });
            return new ArrayList<>(orderedSubjects);
        }

        private HashMap<Subject, List<Long>> generateClassroomListForSubjects() {
            HashMap<Subject, List<Long>> rooms = new HashMap<>();
            service.subjects.stream().forEach(subject -> {
                rooms.put(subject, new ArrayList<>());
            });
            return rooms;
        }
    }

    // TODO: Decide if this should throw an error or return null
    @Override
    public List<Course> generateSchedule() {
        int roomLimit = 20;
        for (; numberOfRooms < roomLimit; numberOfRooms++) {
            if (depthFirstSearch(0)) {
                return convertScheduleToCourses();
            }
        }
        throw new RuntimeException("Unable to form valid schedule");
    }

    // TODO: Add iterative deepening to minimize number of rooms and unique classes
    // TODO: Add logic to add class based on available room/subject
    private boolean depthFirstSearch(int group) {
        // Base case
        if (coursesAdded == totalCourses) {
            return true;
        }

        for (int day = 0; day < DAYS_PER_WEEK; day++) {
            for (int timeslot = 0; timeslot < coursesPerDay; timeslot++) {
                if (schedule[group][day][timeslot] == null) {
                    schedule[group][day][timeslot] = subjectsToAdd.get(subjectIterator);
                    if (hasNoConflict(group, day, timeslot)) {
                        group++;
                        if (group == groups.size()) {
                            coursesAdded++;
                            group = 0;
                            subjectIterator = (subjectIterator + 1) % subjectsToAdd.size();
                        }
                        // If solution is invalid, back track
                        if (!depthFirstSearch(group)) {
                            subjectIterator--;
                            if (subjectIterator < 0) {
                                subjectIterator = subjectsToAdd.size() - 1;
                            }
                            group--;
                            if (group < 0) {
                                group = groups.size() - 1;
                                coursesAdded--;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        schedule[group][day][timeslot] = null;
                    }
                }
            }
        }
        return false;
    }

    // TODO: Change logic to check for subject and room repetition instead of just subject
    private boolean hasNoConflict(int group, int day, int timeslot) {
        if (subjectsWithConstraint.contains(schedule[group][day][timeslot]) && timeslot != coursesPerDay - 1) {
            schedule[group][day][timeslot] = null;
            return false;
        }
        for (int i = 0; i < group; i++) {
            if (schedule[i][day][timeslot] == schedule[group][day][timeslot]) {
                schedule[group][day][timeslot] = null;
                return false;
            }
        }
        return true;
    }

    // TODO: SET TEACHER AND ROOM OBJECTS.
    // TODO: Figure out a way to store and return the timeslot of the class
    private List<Course> convertScheduleToCourses() {
        List<Course> courses = new ArrayList<>();
        IntStream.range(0, groups.size()).forEach(group -> {
            IntStream.range(0, DAYS_PER_WEEK).forEach(day -> {
                IntStream.range(0, coursesPerDay).forEach(timeslot -> {
                    Course course = new Course();
                    course.setGroup(groups.get(group));
                    course.setSubject(schedule[group][day][timeslot]);
                    course.setRoom(null);
                    course.setTeacher(null);
                    courses.add(course);
                });
            });
        });
        return courses;
    }
}
