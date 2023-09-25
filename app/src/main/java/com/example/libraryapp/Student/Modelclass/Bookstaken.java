package com.example.libraryapp.Student.Modelclass;

public class Bookstaken {
    private String SNo;
    private String bookid;
    private String bookname;
    private String authorname;
    private String issuedate;
    private String returndate;
    private  String returnstatus;


    public Bookstaken() {
    }

    public Bookstaken(String SNo, String bookid, String bookname, String authorname, String issuedate, String returndate) {
        this.SNo = SNo;
        this.bookid = bookid;
        this.bookname = bookname;
        this.authorname = authorname;
        this.issuedate = issuedate;
        this.returndate = returndate;
    }

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
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

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
}
