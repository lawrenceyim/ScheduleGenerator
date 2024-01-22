package com.solvd.schedulegenerator.domain;

import com.solvd.schedulegenerator.persistence.SubjectDao;

public class Course {

    private long id;
    private StudentGroup group;
    private Subject subject;
    private Room room;
    private Teacher teacher;

    public Course(){}

    public Course(long id, StudentGroup group, Subject subject, Room room, Teacher teacher) {
        this.id = id;
        this.group = group;
        this.subject = subject;
        this.room = room;
        this.teacher = teacher;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
