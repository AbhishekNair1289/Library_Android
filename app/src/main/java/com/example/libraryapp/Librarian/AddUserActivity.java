package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity {

    EditText stud_name, stud_regno, stud_class, stud_dept, stud_email, stud_phone, stud_dob;
    Button add;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        stud_name=findViewById(R.id.aau_name);
        stud_regno=findViewById(R.id.aau_regno);
        stud_class=findViewById(R.id.aau_class);
        stud_dept=findViewById(R.id.aau_dept);
        stud_email=findViewById(R.id.aau_email);
        stud_phone=findViewById(R.id.aau_mob);
        stud_dob=findViewById(R.id.aau_dob);
        add=findViewById(R.id.aau_addbtn);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        };
        stud_dob.setOnClickListener(v -> new DatePickerDialog(AddUserActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        add.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(getApplicationContext(), "Enter valid data!", Toast.LENGTH_LONG).show();
            } else
                senddata();
        });

    }

    private void senddata(){
        String stu_regno = stud_regno.getText().toString();
        String stu_name = stud_name.getText().toString();
        String stu_class = stud_class.getText().toString();
        String stu_deparment = stud_dept.getText().toString();
        String stu_email = stud_email.getText().toString();
        String stu_mobile =stud_phone.getText().toString();
        String stu_dob = stud_dob.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.addstudent,
                response -> {
                    Log.v("HELLO",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            String error = jobject.getString("error");
                            String msg = jobject.getString("message");
                            if(error.equals("false"))
                            {
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                                stud_name.setText("");
                                stud_regno.setText("");
                                stud_class.setText("");
                                stud_dept.setText("");
                                stud_email.setText("");
                                stud_phone.setText("");
                                stud_dob.setText("");
                                ;
                            }
                            else
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();






                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
//                    newArrivalAdapter = new NewArivalAdap(getContext(), newArrivalsdetailArrayList);
//                    newarivals.setAdapter(newArrivalAdapter);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("regno", stu_regno);
                parmas.put("name", stu_name);
                parmas.put("class", stu_class);
                parmas.put("department", stu_deparment);
                parmas.put("email", stu_email);
                parmas.put("mobile", stu_mobile);
                parmas.put("dob", stu_dob);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        stud_dob.setText(sdf.format(myCalendar.getTime()));
    }

//        EditText , , , , , , ;
    private boolean validate() {
        if (stud_name.getText().toString().trim().equals("")) {
            stud_name.setError("Enter Student name");
            return false;
        } else if (stud_regno.getText().toString().trim().equals("")) {
            stud_regno.setError("Enter Student Regno");
            return false;
        } else if (stud_class.getText().toString().trim().equals("")) {
            stud_class.setError("Enter Student class");
            return false;
        } else if (stud_dept.getText().toString().trim().equals("") ) {
            stud_dept.setError("Enter Department");
            return false;
        } else if (stud_email.getText().toString().trim().equals("")||!isEmailValid(stud_email.getText().toString()) ) {
            stud_dept.setError("Enter Email Id");
            return false;
        } else if (stud_phone.getText().toString().trim().equals("") || !stud_phone.getText().toString().matches("[0-9]{10}")) {
            stud_phone.setError("Enter phone number");
            return false;
        } else if (stud_dob.getText().toString().trim().equals("") ) {
            stud_dept.setError("Enter Date of Birth");
            return false;
        } else
            return true;
    }
    private boolean isEmailValid(String s) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();

    }


}