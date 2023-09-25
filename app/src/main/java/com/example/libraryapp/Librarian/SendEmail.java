package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.Adapters.ListtosendEmailAdapter;
import com.example.libraryapp.Librarian.Adapters.UVBooksTakenAdapter;
import com.example.libraryapp.Librarian.ModelClass.ListtosendEmail;
import com.example.libraryapp.Librarian.ModelClass.UVBookstaken;
import com.example.libraryapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendEmail extends AppCompatActivity {

    RecyclerView recyclerView;
    ListtosendEmail listtosendEmail;
    ArrayList<ListtosendEmail> listtosendEmailArrayList ;
    ListtosendEmailAdapter listtosendEmailAdapter;
    Calendar myCalendar;
    String curdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        recyclerView=findViewById(R.id.ase_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        curdate = sdf.format(new Date());

//        Toast.makeText(this, curdate, Toast.LENGTH_SHORT).show();
//        returndateedt.setText(currentDateandTime);

        getDetails();


    }

    private void getDetails() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Config.viewnotreturnedstudents, response -> {

            Log.d("Responseviewnot", response);
            String error = "";
            if(response!=null)
            {
                try {
                    JSONObject jobject=new JSONObject(response);
                    JSONArray jsonArray = jobject.getJSONArray("details");
                    listtosendEmailArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        listtosendEmail = new ListtosendEmail();

                        JSONObject jobj=jsonArray.getJSONObject(i);

                        String issue_id = jobj.getString("issue_id");
                        String issue_regno = jobj.getString("issue_regno");
                        String issue_name = jobj.getString("issue_name");
                        String issue_date = jobj.getString("issue_date");
                        String book__id = jobj.getString("book__id");
                        String book_names = jobj.getString("book_names");
                        String book_author = jobj.getString("book_author");
                        String issue_status = jobj.getString("issue_status");
//                        Toast.makeText(this, issue_id, Toast.LENGTH_SHORT).show();

                        listtosendEmail.setSlno(String.valueOf(i+1));
                        listtosendEmail.setStuid(issue_regno);
                        listtosendEmail.setStuname(issue_name);
                        listtosendEmail.setIssuedate(issue_date);
                        listtosendEmail.setBookid(book__id);
                        listtosendEmail.setBookname(book_names);
                        listtosendEmail.setAuthername(book_author);
                        listtosendEmail.setIssuestatus(issue_status);

                        listtosendEmailArrayList.add(listtosendEmail);
//                        Toast.makeText(this, listtosendEmailArrayList.get(i).getBookid(), Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            }
            listtosendEmailAdapter = new ListtosendEmailAdapter(getApplicationContext(), listtosendEmailArrayList);
            recyclerView.setAdapter(listtosendEmailAdapter); // set the Adapter to RecyclerView

        }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);

    }
}