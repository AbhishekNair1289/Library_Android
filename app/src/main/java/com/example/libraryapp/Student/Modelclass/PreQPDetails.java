package com.example.libraryapp.Student.Modelclass;

public class PreQPDetails {
    String 	qp_id, qp_year,qp_sem , qp_department, qp_subject, 	qp_questionpaper;

    public PreQPDetails(String qp_id, String qp_year, String qp_sem, String qp_department, String qp_subject, String qp_questionpaper) {
        this.qp_id = qp_id;
        this.qp_year = qp_year;
        this.qp_sem = qp_sem;
        this.qp_department = qp_department;
        this.qp_subject = qp_subject;
        this.qp_questionpaper = qp_questionpaper;
    }

    public PreQPDetails() {
    }

    public String getQp_id() {
        return qp_id;
    }

    public void setQp_id(String qp_id) {
        this.qp_id = qp_id;
    }

    public String getQp_year() {
        return qp_year;
    }

    public void setQp_year(String qp_year) {
        this.qp_year = qp_year;
    }

    public String getQp_sem() {
        return qp_sem;
    }

    public void setQp_sem(String qp_sem) {
        this.qp_sem = qp_sem;
    }

    public String getQp_department() {
        return qp_department;
    }

    public void setQp_department(String qp_department) {
        this.qp_department = qp_department;
    }

    public String getQp_subject() {
        return qp_subject;
    }

    public void setQp_subject(String qp_subject) {
        this.qp_subject = qp_subject;
    }

    public String getQp_questionpaper() {
        return qp_questionpaper;
    }

    public void setQp_questionpaper(String qp_questionpaper) {
        this.qp_questionpaper = qp_questionpaper;
    }
}
