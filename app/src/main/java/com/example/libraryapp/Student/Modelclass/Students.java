package com.example.libraryapp.Student.Modelclass;

public class Students {
    private int stu_id;
    private String stu_regno, stu_name, stu_class, stu_deparment, stu_email, stu_mobile, stu_dob;

    public Students(int stu_id, String stu_regno, String stu_name) {
        this.stu_id = stu_id;
        this.stu_regno = stu_regno;
        this.stu_name = stu_name;
    }

    public Students() {

    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_regno() {
        return stu_regno;
    }

    public void setStu_regno(String stu_regno) {
        this.stu_regno = stu_regno;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }

    public String getStu_deparment() {
        return stu_deparment;
    }

    public void setStu_deparment(String stu_deparment) {
        this.stu_deparment = stu_deparment;
    }

    public String getStu_email() {
        return stu_email;
    }

    public void setStu_email(String stu_email) {
        this.stu_email = stu_email;
    }

    public String getStu_mobile() {
        return stu_mobile;
    }

    public void setStu_mobile(String stu_mobile) {
        this.stu_mobile = stu_mobile;
    }

    public String getStu_dob() {
        return stu_dob;
    }

    public void setStu_dob(String stu_dob) {
        this.stu_dob = stu_dob;
    }
}
