package com.solvd.schedulegenerator.domain;


public class Subject {
    private long id;
    private String name;

    private boolean shouldBeLast; // Flag for if a subject should be last in the day

    public Subject() {
    }

    public Subject(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){this.name = name;}

    public boolean getShouldBeLast() {
        return shouldBeLast;
    }

    public void setShouldBeLast(boolean shouldBeLast) {
        this.shouldBeLast = shouldBeLast;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
