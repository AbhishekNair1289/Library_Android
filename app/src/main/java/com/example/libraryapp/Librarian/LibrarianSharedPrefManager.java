package com.example.libraryapp.Librarian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.libraryapp.Librarian.ModelClass.Librarian;

public class LibrarianSharedPrefManager {
    private static final String SHARED_PREF_NAME = "LIBRARIANDETAILS";
    private static final String USER_ID = "lib_id";
    private static final String USER_NAME = "lib_name";


    private static LibrarianSharedPrefManager mInstance;
    private final Context context;

    public LibrarianSharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized LibrarianSharedPrefManager getInstance1(Context context){
        if (mInstance==null){
            mInstance=new LibrarianSharedPrefManager(context);
        }
        return mInstance;
    }

    public void librarianLogin(Librarian user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, user.getLib_id());
        editor.putString(USER_NAME, user.getLib_name());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_ID, 0) != 0;
    }

    //this method will give the logged in user
    public Librarian getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Librarian(
                sharedPreferences.getInt(USER_ID, -1),
                sharedPreferences.getString(USER_NAME, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LibrarianLogin.class));
    }
}
