package com.parentsphere.connect;

public class Parent {
    private String parentName;
    private String email;
    private String phone;
    private String studentName;
    private String studentClass;
    private String studentYear;

    // Default Constructor (Required for Firebase)
    public Parent() {
    }

    // Parameterized Constructor
    public Parent(String parentName, String email, String phone, String studentName, String studentClass, String studentYear) {
        this.parentName = parentName;
        this.email = email;
        this.phone = phone;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentYear = studentYear;
    }

    // Getter Methods
    public String getParentName() {
        return parentName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getStudentYear() {
        return studentYear;
    }

    // Setter Methods
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    // Validation Methods
    public boolean isEmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPhoneValid() {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    // Override toString for Debugging
    @Override
    public String toString() {
        return "Parent{" +
                "parentName='" + parentName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", studentYear='" + studentYear + '\'' +
                '}';
    }
}
