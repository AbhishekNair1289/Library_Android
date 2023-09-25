package com.example.libraryapp.Student;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.HomeActivity;
import com.example.libraryapp.Librarian.LibrarianSharedPrefManager;
import com.example.libraryapp.Librarian.LibraryHomeActivity;
import com.example.libraryapp.Librarian.ModelClass.Librarian;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Modelclass.Students;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentsLogin extends AppCompatActivity {

    EditText reg_et, dob_et;
    Button login;
    String regno, DOB;
    ProgressBar simpleProgressBar;
    Students students;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_login);

        if(StudentSharedPrefManager.getInstance1(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, StudentHomepage.class));
        }

        reg_et=findViewById(R.id.asl_userid_et);
        dob_et =findViewById(R.id.asl_password_et);
        login=findViewById(R.id.asl_login_btn);
        simpleProgressBar = (ProgressBar) findViewById(R.id.asl_simpleProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(StudentsLogin.this, "Enter Valid data", Toast.LENGTH_SHORT).show();
                } else
                    login();
//                Toast.makeText(StudentsLogin.this, "Success", Toast.LENGTH_SHORT).show();
            }

            private void login() {
                simpleProgressBar.setVisibility(View.VISIBLE);

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.userlogin_url, response -> {

                    Log.d("Response", response);
                    simpleProgressBar.setVisibility(View.INVISIBLE);

                    String error = "";
                    if (response != null) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            error = jsonObject.getString("error");
                            if (error.contains("true")) {
                                Toast.makeText(StudentsLogin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if (error.contains("false")) {
                                JSONObject jsonArray= jsonObject.getJSONObject("details");

                                students=new Students(jsonArray.getInt("stu_id"), jsonArray.getString("stu_regno"), jsonArray.getString("stu_name"));
                                StudentSharedPrefManager.getInstance1(getApplicationContext()).StudentLogin(students);

                                Toast.makeText(StudentsLogin.this,jsonArray.getString("stu_regno") , Toast.LENGTH_SHORT).show();
                                reg_et.setText("");
                                dob_et.setText("");

                                Intent intent = new Intent(getApplicationContext(), StudentHomepage.class);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            Log.e("Error>>>111", error);

                            e.printStackTrace();
                        }
                    }
                }, error -> {
                    Log.e("Error", error.toString());
                    Toast.makeText(StudentsLogin.this, error.toString(), Toast.LENGTH_SHORT).show();
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parmas = new HashMap<>();

                        //here we pass params
                        parmas.put("regno", regno);
                        parmas.put("stu_dob", DOB);

                        return parmas;
                    }
                };
                requestQueue.add(stringRequest);
            }

            private boolean validate() {
                regno=reg_et.getText().toString().trim();
                DOB=dob_et.getText().toString().trim();


                if (TextUtils.isEmpty(regno)){
                    reg_et.setError("Register No is required.");
                    return false;
                }
                else if (TextUtils.isEmpty(DOB)){
                    dob_et.setError("Date of Birth is required");
                    return false;
                }
                else
                    return true;
            }
        });

    }
    @Override
    public void onBackPressed() {
        StudentsLogin.super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}