package com.solvd.schedulegenerator.service.impl;

import com.solvd.schedulegenerator.domain.Course;
import com.solvd.schedulegenerator.domain.StudentGroup;
import com.solvd.schedulegenerator.domain.Subject;
import com.solvd.schedulegenerator.service.ScheduleGenerationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class ScheduleGenerationServiceGeneticAlgo implements ScheduleGenerationService {
    // Each element represents timeslot. Grouped by student group and day. Use % to find specific class
    private Long[] schedule; // Subjects represented by their id
    private List<StudentGroup> groups;
    private List<Subject> subjects;  // All subjects
    private List<Subject> subjectsWithConstraint;  // Subjects with constraints
    private int coursesPerDay;  // No more than 5 per day
    private final int DAYS_PER_WEEK = 5;
    private List<Long> subjectsToAdd;  // Iterate over this linked hashset to add courses to schedule
    private int totalCourses;
    private int numberOfRooms;
    private int[] coursesAddedToGroup;
    private HashMap<Long, Subject> subjectIdMap;
    private HashSet<List<Long>> tabuList; // Similar to Tabu search, maintain a list of previously seen solutions

    private ScheduleGenerationServiceGeneticAlgo() {
    }

    public static class Builder {
        private final ScheduleGenerationServiceGeneticAlgo service;

        public Builder() {
            this.service = new ScheduleGenerationServiceGeneticAlgo();
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setGroups(List<StudentGroup> groups) {
            service.groups = groups;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setSubjects(List<Subject> subjects) {
            service.subjects = subjects;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setSubjectsWithConstraints(List<Subject> subjectsWithConstraint) {
            service.subjectsWithConstraint = subjectsWithConstraint;
            return this;
        }

        public ScheduleGenerationServiceGeneticAlgo.Builder setCoursesPerDay(int coursesPerDay) {
            service.coursesPerDay = coursesPerDay;
            service.totalCourses = service.coursesPerDay * service.DAYS_PER_WEEK;
            service.schedule = new Long[service.totalCourses * service.groups.size()];
            service.numberOfRooms = service.subjects.size();
            service.subjectsToAdd = sortSubjectsByPriority();
            service.coursesAddedToGroup = new int[service.groups.size()];
            service.subjectIdMap = generateSubjectIdMap();
            service.tabuList = new HashSet<>();
            return this;
        }

        public ScheduleGenerationService build() {
            return service;
        }

        // Class with constraints go first. Then classes without constraints
        private List<Long> sortSubjectsByPriority() {
            LinkedHashSet<Long> orderedSubjects = new LinkedHashSet<>();
            service.subjectsWithConstraint.stream().forEach(subject -> {
                orderedSubjects.add(subject.getId());
            });
            service.subjects.stream().forEach(subject -> {
                orderedSubjects.add(subject.getId());
            });
            return new ArrayList<>(orderedSubjects);
        }

        private HashMap<Long, Subject> generateSubjectIdMap() {
            HashMap<Long, Subject> hashMap = new HashMap<>();
            service.subjects.stream().forEach(subject -> {
                hashMap.put(subject.getId(), subject);
            });
            return hashMap;
        }
    }

    @Override
    public List<Course> generateSchedule() {
        return null;
    }

    // Return the schedule index based on the group and timeslot
    private int findIndexByGroupAndTimeslot(int group, int timeslot) {
        return group * subjects.size() + timeslot;
    }

    private Long findSubjectByGroupAndTimeslot(int group, int timeslot) {
        return schedule[findIndexByGroupAndTimeslot(group, timeslot)];
    }

    private void setSubjectByGroupAndTimeslot(int group, int timeslot, Long subjectId) {
        schedule[findIndexByGroupAndTimeslot(group, timeslot)] = subjectId;
    }

    private List<Subject> convertIdToSubject() {
        List<Subject> list = new LinkedList<>();
        Arrays.stream(schedule).forEach(id -> {
            list.add(subjectIdMap.get(id));
        });
        return list;
    }

    private void addToTabuList(Long[] state) {
        tabuList.add(Arrays.asList(state));
    }

    private boolean isInTabuList(Long[] state) {
        return tabuList.contains(state);
    }
}
