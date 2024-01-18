package com.solvd.schedulegenerator.domain;

import java.rmi.StubNotFoundException;

public class Subject {
    private Long id;
    private String name;
    private Teacher teacher;
    private Long roomId;

    public Subject(){}

    public Subject(Long id, Long roomId, String name, Teacher teacher) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", roomId=" + roomId +
                '}';
    }
}
