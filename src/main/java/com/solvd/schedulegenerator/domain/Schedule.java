package com.solvd.schedulegenerator.domain;

public class Schedule {

    private long id;
    private Course course;
    private DayOfWeek dayOfWeek;
    private short timeSlot;

    public Schedule() {
    }

    public Schedule(long id, Course course, DayOfWeek dayOfWeek, short timeSlot) {
        this.id = id;
        this.course = course;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
    }

    public short getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(short timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", course=" + course +
                ", dayOfWeek=" + dayOfWeek +
                ", timeSlot=" + timeSlot +
                '}';
    }
}
