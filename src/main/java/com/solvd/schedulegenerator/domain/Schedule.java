package com.solvd.schedulegenerator.domain;

public class Schedule {
    private long groupId;
    private long subjectId;
    private long timeSlot;
    private String dayOfWeek;

    public Schedule(long groupId, long subjectId, long timeSlot, String dayOfWeek) {
        this.groupId = groupId;
        this.subjectId = subjectId;
        this.timeSlot = timeSlot;
        this.dayOfWeek = dayOfWeek;
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

    public long getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(long timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
