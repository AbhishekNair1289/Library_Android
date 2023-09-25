package com.example.libraryapp.Librarian.ModelClass;

import android.widget.TextView;

public class ListtosendEmail {
    String slno, stuid, stuname, bookid, bookname, authername, issuedate, issuestatus;

    public ListtosendEmail(String slno, String stuid, String stuname, String bookid, String bookname, String authername, String issuedate, String issuestatus) {
        this.slno = slno;
        this.stuid = stuid;
        this.stuname = stuname;
        this.bookid = bookid;
        this.bookname = bookname;
        this.authername = authername;
        this.issuedate = issuedate;
        this.issuestatus = issuestatus;
    }

    public ListtosendEmail() {

    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthername() {
        return authername;
    }

    public void setAuthername(String authername) {
        this.authername = authername;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getIssuestatus() {
        return issuestatus;
    }

    public void setIssuestatus(String issuestatus) {
        this.issuestatus = issuestatus;
    }
}
