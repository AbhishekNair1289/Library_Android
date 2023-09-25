package com.example.libraryapp.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.libraryapp.Librarian.LibrarianLogin;
import com.example.libraryapp.Librarian.LibrarianSharedPrefManager;
import com.example.libraryapp.Librarian.ModelClass.Librarian;
import com.example.libraryapp.Student.Modelclass.Students;

public class StudentSharedPrefManager {
    private static final String SHARED_PREF_NAME = "STUDENTSDETAILS";
    private static final String USER_ID = "stu_id";
    private static final String USER_NAME = "stu_name";
    private static final String USER_REGNO = "stu_regno";


    private static StudentSharedPrefManager mInstance;
    private final Context context;

    public StudentSharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized StudentSharedPrefManager getInstance1(Context context){
        if (mInstance==null){
            mInstance=new StudentSharedPrefManager(context);
        }
        return mInstance;
    }

    public void StudentLogin(Students user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, user.getStu_id());
        editor.putString(USER_REGNO, user.getStu_regno());
        editor.putString(USER_NAME, user.getStu_name());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_REGNO, null) != null;
    }

    //this method will give the logged in user
    public Students getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Students(
                sharedPreferences.getInt(USER_ID, -1),
                sharedPreferences.getString(USER_REGNO, null),
                sharedPreferences.getString(USER_NAME, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, StudentsLogin.class));
    }
}
