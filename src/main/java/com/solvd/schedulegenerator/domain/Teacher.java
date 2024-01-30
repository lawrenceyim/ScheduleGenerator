package com.solvd.schedulegenerator.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Teacher  {

    private final Logger OUTPUT_LOGGER = LogManager.getLogger(Teacher.class);

    private long id;
    private String firstName;
    private String lastName;
    private long subjectId;

    public Teacher() {
    }

    public Teacher(long id, String firstName, String lastName, long subjectId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjectId = subjectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
