package com.universityportal;

public class Course {
    private int id;
    private String name;
    private String department;
    private int credits;

    public Course(String name, String department, int credits) {
        this.name = name;
        this.department = department;
        this.credits = credits;
    }

    public Course(int id, String name, String department, int credits) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
