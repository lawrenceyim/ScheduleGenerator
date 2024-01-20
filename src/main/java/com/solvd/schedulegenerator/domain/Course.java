package com.solvd.schedulegenerator.domain;

import com.solvd.schedulegenerator.persistence.SubjectDao;

public class Course {
    private long id;
    private StudentGroup group;
    private Subject subject;
    private long timeSlot;
    private DayOfWeek dayOfWeek;

    public Course(){}

    public Course(long id, StudentGroup group, Subject subject, long timeSlot, String dayOfWeek) {
        this.id = id;
        this.group = group;
        this.subject = subject;
        this.timeSlot = timeSlot;
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public long getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(long timeSlot) {
        this.timeSlot = timeSlot;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", group=" + group +
                ", subject=" + subject +
                ", timeSlot=" + timeSlot +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
