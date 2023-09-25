package com.example.libraryapp;

public class Config {
    public static String base_url="http://192.168.10.104/Library/";
    public static String imgbase_url="http://192.168.10.104/Library/Picture/";
    public static String pdfbase_url="http://192.168.10.104/Library/Pdf/";
//   http://192.168.18.5/Library/userlogin.php
    public static String userlogin_url=base_url+"userlogin.php";
    public static String librarianlogin_url=base_url+"liblogin.php";
    public static String viewissuedbook=base_url+"view_issued_books.php";
    public static String viewnewarrivals=base_url+"view_new_book.php";
    public static String viewsinglebookdetails=base_url+"view_single_id.php";
    public static String view_idorname=base_url+"view_idorname.php";
    public static String viewcategories=base_url+"view_cate.php";
    public static String view_book_bycat=base_url+"view_book_bycat.php";
    public static String view_single_student=base_url+"view_single_student.php";
    public static String addbook=base_url+"addbooks.php";
    public static String addbookimage=base_url+"addbookimage.php";
    public static String updatebook=base_url+"updatebook.php";
    public static String addstudent=base_url+"userregister.php";
    public static String issuebook=base_url+"issue_book.php";
    public static String updateissue=base_url+"return.php";

    public static String returnbook=base_url+"update_issue1.php";
    public static String addquestionpaper=base_url+"addquestionpaper.php";

    public static String view_all_qns=base_url+"view_single_qn.php";
    public static String update_copies=base_url+"update_copies.php";
    public static String view_all_books=base_url+"view_all_books.php";
    public static String view_department=base_url+"view_department.php";
    public static String view_sem=base_url+"view_sem.php";
    public static String view_subject=base_url+"view_subject.php";
    public static String viewnotreturnedstudents=base_url+"Fine.php";
    public static String sendemail=base_url+"sendemail.php";

//
}
