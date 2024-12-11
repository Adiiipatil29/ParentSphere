package com.parentsphere.connect;
public class Attendance {
    private String studentId;
    private String studentName;
    private String date;
    private boolean present;

    // Constructor
    public Attendance(String studentId, String studentName, String date, boolean present) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.present = present;
    }

    // Default constructor for Firebase
    public Attendance() { }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
