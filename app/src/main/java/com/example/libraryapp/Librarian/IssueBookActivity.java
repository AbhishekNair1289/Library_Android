package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IssueBookActivity extends AppCompatActivity {
    EditText bname, bauthor, bookid, stuid, stuname, issuedate;
    ImageView bookimage;
    Button add;
    String sbname, sbauthor, sbookid, sstuid, sstuname, sissuedate;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        sstuid=getIntent().getExtras().getString("regid");
        sstuname=getIntent().getExtras().getString("name");

        bname=findViewById(R.id.aib_bookname);
        bauthor=findViewById(R.id.aib_bookauthor);
        bookid=findViewById(R.id.aib_bookid);
        stuid=findViewById(R.id.aib_stuid);
        stuname=findViewById(R.id.aib_stuname);
        issuedate=findViewById(R.id.aib_issuedate);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        };
        issuedate.setOnClickListener((View v) -> {
            new DatePickerDialog(IssueBookActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        stuname.setText(sstuname);
        stuid.setText(sstuid);

        add=findViewById(R.id.aib_addbtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "Enter valid data!", Toast.LENGTH_LONG).show();
                } else
                    senddata();
            }
        });
    }

    private void senddata() {

        sbname= bname.getText().toString();
        sbauthor= bauthor.getText().toString();
        sbookid= bookid.getText().toString();
        sissuedate= issuedate.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.issuebook,
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
                                bname.setText("");
                                bauthor.setText("");
                                stuid.setText("");
                                stuname.setText("");
                                bookid.setText("");
                                issuedate.setText("");
                                removebookfromtable();

                            }
                            else
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("regno", sstuid);
                parmas.put("name",sstuname);
                parmas.put("bookid",sbookid );
                parmas.put("bname",sbname );
                parmas.put("author",sbauthor );
                parmas.put("idate",sissuedate );

                return parmas;
            }
        };
        queue.add(stringRequest);
    }

    private void removebookfromtable() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.update_copies,
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
//
                                Intent intent=new Intent(getApplicationContext(), ViewUserActivity.class);
                                intent.putExtra("regid", sstuid);
                                startActivity(intent);

                            }
                            else
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("Id",sbookid );
                return parmas;
            }
        };
        queue.add(stringRequest);
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        issuedate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean validate() {
        if (bname.getText().toString().trim().equals("")) {
            bname.setError("Enter Book name");
            return false;
        } else if (bauthor.getText().toString().trim().equals("")) {
            bauthor.setError("Enter Book Author");
            return false;
        } else if (bookid.getText().toString().trim().equals("")) {
            bookid.setError("Enter Book ID");
            return false;
        } else if (stuid.getText().toString().trim().equals("") ) {
            stuid.setError("Enter Student ID");
            return false;
        }  else if (issuedate.getText().toString().trim().equals("") ) {
            issuedate.setError("Enter Issue Date");
            return false;
        } else if (stuname.getText().toString().trim().equals("") ) {
            stuname.setError("Enter Student Name");
            return false;
        } else
            return true;
    }

}