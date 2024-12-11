package com.parentsphere.connect;

// Teacher.java
public class Teacher{
    private String name;
    private String course;
    private String department;

    public Teacher() {
        // Default constructor required for Firebase
    }

    public Teacher(String name, String course, String department) {
        this.name = name;
        this.course = course;
        this.department = department;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getDepartment() {
        return department;
    }
}
