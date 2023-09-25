package com.example.libraryapp.Librarian;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.libraryapp.HomeActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.StudentHomepage;
import com.example.libraryapp.Student.StudentsLogin;

public class LibraryHomeActivity extends AppCompatActivity {

    ImageButton addbook, viewbook, updatebook, adduser, viewuser, addqp;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_home);

        addbook=findViewById(R.id.alh_addbook);
        viewbook=findViewById(R.id.alh_viewbook);
        updatebook=findViewById(R.id.alh_updatebook);
        adduser=findViewById(R.id.alh_adduser);
        viewuser=findViewById(R.id.alh_viewuser);
        addqp=findViewById(R.id.alh_addqp);

        toolbar=findViewById(R.id.toolbarlayout);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ADDBOOK.class);
                startActivity(intent);
            }
        });
        viewbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewBookActivity.class);
                startActivity(intent);
            }
        });
        updatebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UpdateBookActivity.class);
                startActivity(intent);
            }
        });
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(intent);
            }
        });
        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewUserActivity.class);
                startActivity(intent);
            }
        });
        addqp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddQPActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            LibrarianSharedPrefManager.getInstance1(getApplicationContext()).logout();
            return true;
        }else if (id==R.id.menu_sendmail){
            Intent intent = new Intent(getApplicationContext(), SendEmail.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        LibraryHomeActivity.super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}