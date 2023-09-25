package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;

import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.HomeActivity;
import com.example.libraryapp.Librarian.ModelClass.Librarian;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.StudentsLogin;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LibrarianLogin extends AppCompatActivity {

    EditText userid_et, password_et;
    Button login;
    String userid, password;
    ProgressBar simpleProgressBar;
    Librarian librarian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian_login);

        if(LibrarianSharedPrefManager.getInstance1(this).isLoggedIn()){

            startActivity(new Intent(this, LibraryHomeActivity.class));
            finish();
        }

        userid_et=findViewById(R.id.all_userid_et);
        password_et =findViewById(R.id.all_password_et);
        login=findViewById(R.id.all_login_btn);
        simpleProgressBar = (ProgressBar) findViewById(R.id.all_simpleProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(LibrarianLogin.this, "Enter Valid data", Toast.LENGTH_SHORT).show();
                } else
                    login();

            }

            private void login() {
                simpleProgressBar.setVisibility(View.VISIBLE);

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.librarianlogin_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        simpleProgressBar.setVisibility(View.INVISIBLE);

                        Log.d("Response", response);
                        String error = "";
                        if (response != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                error = jsonObject.getString("error");
                                if (error.contains("true")) {
                                    Toast.makeText(LibrarianLogin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (error.contains("false")) {
                                    JSONObject jsonArray= jsonObject.getJSONObject("details");

                                    librarian=new Librarian(jsonArray.getInt("lib_id"), jsonArray.getString("lib_name"));
                                    LibrarianSharedPrefManager.getInstance1(getApplicationContext()).librarianLogin(librarian);

                                    userid_et.setText("");
                                    password_et.setText("");
//
                                    Intent intent = new Intent(getApplicationContext(), LibraryHomeActivity.class);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(LibrarianLogin.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parmas = new HashMap<>();

                        //here we pass params
                        parmas.put("uname", userid);
                        parmas.put("password", password);

                        return parmas;
                    }
                };
                int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

                RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);

                requestQueue.add(stringRequest);
            }

            private boolean validate() {
                userid=userid_et.getText().toString().trim();
                password=password_et.getText().toString().trim();


                if (TextUtils.isEmpty(userid)){
                    userid_et.setError("User Id is required.");
                    return false;
                }
                else if (TextUtils.isEmpty(password)){
                    password_et.setError("Password is required");
                    return false;
                }
                else
                    return true;
            }
        });


    }
    @Override
    public void onBackPressed() {
        LibrarianLogin.super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}