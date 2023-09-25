package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;

import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.Adapters.UVBooksTakenAdapter;
import com.example.libraryapp.Librarian.ModelClass.UVBookstaken;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.BooksTakenAdapter;
import com.example.libraryapp.Student.Modelclass.Bookstaken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewUserActivity extends AppCompatActivity {

    EditText searchbookid;
    ImageView searchbtn;
    TextView stud_name, stud_dept;
    Button issuebook, returnbook;
    String stuid;
    RecyclerView recyclerView;
    UVBookstaken bookstaken;
    List<UVBookstaken> bookstakenArrayList = new ArrayList<>();
    UVBooksTakenAdapter customAdapter;
    String id,name,deparment,passedstuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        searchbookid=findViewById(R.id.avu_searchbookedt);
        searchbtn=findViewById(R.id.avu_searchbtn);
        stud_dept=findViewById(R.id.avu_studept);
        stud_name=findViewById(R.id.avu_stuname);
        issuebook=findViewById(R.id.avu_issuebtn);
        returnbook=findViewById(R.id.avu_returnbtn);
        recyclerView=findViewById(R.id.avu_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

//        if (getIntent().getExtras().getString("regid")!=null){
//            passedstuid=getIntent().getExtras().getString("regid");
//            getdetails(passedstuid);
//        }


        recyclerView.setVisibility(View.INVISIBLE);
        issuebook.setVisibility(View.INVISIBLE);
        returnbook.setVisibility(View.INVISIBLE);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuid=searchbookid.getText().toString();
                getdetails(stuid);

            }
        });

        issuebook.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), IssueBookActivity.class);
            intent.putExtra("regid", id);
            intent.putExtra("name", name);
            startActivity(intent);
        });

    }

    private void getdetails(String stuid1) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.view_single_student,
                response -> {
                    Log.v("HELLO",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            JSONArray jsonArray = jobject.getJSONArray("studentdetails");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobject1=jsonArray.getJSONObject(i);

                                 id = jobject1.getString("stu_regno");
                                 name = jobject1.getString("stu_name");
                                 deparment = jobject1.getString("stu_deparment");

                                stud_name.setText(name);
                                stud_dept.setText(deparment);

                                recyclerView.setVisibility(View.VISIBLE);
                                issuebook.setVisibility(View.VISIBLE);
//                                returnbook.setVisibility(View.VISIBLE);
                                viewtakenbooks(stuid1);

                            }


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
                parmas.put("regno", stuid1);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }

    private void viewtakenbooks(String stuid1) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.viewissuedbook, response -> {

            Log.d("Response123", response);
            String error = "";
            if(response!=null)
            {
                try {
                    JSONObject jobject=new JSONObject(response);
                    JSONArray jsonArray = jobject.getJSONArray("details");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        bookstaken = new UVBookstaken();

                        JSONObject jobj=jsonArray.getJSONObject(i);


                        String issue_id = jobj.getString("issue_id");
                        String issue_bookid = jobj.getString("issue_bookid");
                        String issue_bookname = jobj.getString("issue_bookname");
                        String issue_author = jobj.getString("issue_author");
                        String issue_date = jobj.getString("issue_date");
                        String issue_returndate = jobj.getString("issue_returndate");
                        String issue_returnstatus = jobj.getString("issue_status");
//                        Toast.makeText(this, issue_id, Toast.LENGTH_SHORT).show();

                        bookstaken.setIssue_id(issue_id);
                        bookstaken.setBookid(issue_bookid);
                        bookstaken.setAuthorname(issue_author);
                        bookstaken.setBookname(issue_bookname);
                        bookstaken.setIssuedate(issue_date);
                        bookstaken.setReturndate(issue_returndate);
                        bookstaken.setSNo(String.valueOf(i+1));
                        bookstaken.setReturnstatus(issue_returnstatus);

                        bookstakenArrayList.add(bookstaken);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            }
            customAdapter = new UVBooksTakenAdapter(getApplicationContext(), bookstakenArrayList);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("regno", stuid1);
//                parmas.put("stu_dob", DOB);

                return parmas;
            }
        };
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LibraryHomeActivity.class);
        startActivity(intent);
    }
}