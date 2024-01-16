package com.solvd.schedulegenerator.domain;

public class Subject {
    private long id;
    private long roomId;
    private String name;

    public Subject(long id, long roomId, String name) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
