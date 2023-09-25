package com.example.libraryapp.Student.ui;

import android.content.Intent;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.BooksTakenAdapter;
import com.example.libraryapp.Student.Modelclass.Bookstaken;
import com.example.libraryapp.Student.Modelclass.Students;
import com.example.libraryapp.Student.StudentHomepage;
import com.example.libraryapp.Student.StudentSharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.ID;


public class BooksTaken extends Fragment {

    String userid;

    RecyclerView recyclerView;
    Bookstaken bookstaken;
    List<Bookstaken> bookstakenArrayList = new ArrayList<>();
    BooksTakenAdapter customAdapter;
    ProgressBar simpleProgressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_books_taken, container, false);

       Students students = StudentSharedPrefManager.getInstance1(getContext()).getUser();
        userid=students.getStu_regno();
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.bookstaken_simpleProgressBar);

        Toast.makeText(getContext(), userid, Toast.LENGTH_SHORT).show();
        recyclerView=view.findViewById(R.id.bookstakenfragment_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        viewtakenbooks();

        return view;

    }

    private void viewtakenbooks() {
        simpleProgressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.viewissuedbook, response -> {

            Log.d("Response123", response);
            simpleProgressBar.setVisibility(View.INVISIBLE);

            String error = "";
            if(response!=null)
            {
                try {
                    JSONObject jobject=new JSONObject(response);
                    JSONArray jsonArray = jobject.getJSONArray("details");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        bookstaken = new Bookstaken();

                        JSONObject jobj=jsonArray.getJSONObject(i);


                        String issue_id = jobj.getString("issue_id");
                        String issue_bookid = jobj.getString("issue_bookid");
                        String issue_bookname = jobj.getString("issue_bookname");
                        String issue_author = jobj.getString("issue_author");
                        String issue_date = jobj.getString("issue_date");
                        String issue_returndate = jobj.getString("issue_returndate");
                        String issue_returnstatus = jobj.getString("issue_status");


                        bookstaken.setBookid(issue_bookid);
                        bookstaken.setAuthorname(issue_author);
                        bookstaken.setBookname(issue_bookname);
                        bookstaken.setIssuedate(issue_date);
                        bookstaken.setReturndate(issue_returndate);
                        bookstaken.setSNo(String.valueOf(i+1));
                        bookstaken.setReturnstatus(issue_returnstatus);

//                                    newArrivalsdetail.setSImagePath(sImagePath);
//                                    newArrivalsdetail.setISubCategory(iSubCategory);
//                                    newArrivalsdetail.setFPrice(fPrice);
//                                    newArrivalsdetail.setSShortDescription(sShortDescription);
//                                    newArrivalsdetail.setSLongDescription(sLongDescription);
//                                    newArrivalsdetail.setSAltLongDescription(sAltLongDescription);
//                                    newArrivalsdetail.setSAltShortDescription(sAltShortDescription);

                        bookstakenArrayList.add(bookstaken);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            }
            customAdapter = new BooksTakenAdapter(getContext(), bookstakenArrayList);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        }, error -> {
            Log.e("Error", error.toString());
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("regno", userid);
//                parmas.put("stu_dob", DOB);

                return parmas;
            }
        };
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);

    }
}