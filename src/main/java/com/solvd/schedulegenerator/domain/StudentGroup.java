package com.solvd.schedulegenerator.domain;

import java.util.ArrayList;
import java.util.List;

public class StudentGroup {

    private long id;
    private List<Student> students = new ArrayList<>();

    public StudentGroup() {
    }

    public StudentGroup(long id, List<Student> students) {
        this.id = id;
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "id=" + id +
                ", students=" + students +
                '}';
    }
}
