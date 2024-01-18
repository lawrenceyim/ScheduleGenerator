package com.solvd.schedulegenerator.domain;

public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private StudentGroup group;

    public Student(){}

    public Student(Long id, String firstName, String lastName, StudentGroup group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }
}
