package com.solvd.schedulegenerator.domain;

public class Room {
    private long id;
    private String building;
    private int floor;
    private int roomNumber;

    public Room(){}

    public Room(long id, String building, int floor, int roomNumber) {
        this.id = id;
        this.building = building;
        this.floor = floor;
        this.roomNumber = roomNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", building='" + building + '\'' +
                ", floor=" + floor +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
