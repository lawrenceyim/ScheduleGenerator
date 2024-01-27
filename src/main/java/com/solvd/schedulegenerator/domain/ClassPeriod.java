package com.solvd.schedulegenerator.domain;

public class ClassPeriod {
    private long teacherId;
    private long roomId;
    private long groupId;
    private long subjectId;
    private int timeslot; // Represents a specific period in the week

    public ClassPeriod(long teacherId, long roomId, long groupId, long subjectId, int timeslot) {
        this.teacherId = teacherId;
        this.roomId = roomId;
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.timeslot = timeslot;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public int getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(int timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public String toString() {
        return "ClassPeriod{" +
                "teacherId=" + teacherId +
                ", roomId=" + roomId +
                ", groupId=" + groupId +
                ", subjectId=" + subjectId +
                ", timeslot=" + timeslot +
                '}';
    }


}
