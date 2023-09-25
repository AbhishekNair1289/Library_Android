package com.example.libraryapp.Student.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.PreQPAdapter;
import com.example.libraryapp.Student.Modelclass.PreQPDetails;
import com.example.libraryapp.Student.Modelclass.SpaceTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPreviousyearQP extends Fragment {

    RecyclerView recyclerView;
    private static final String MY_PREFS_NAME = "StudentsDetails";
    String userid;

    ProgressBar simpleProgressBar;

    PreQPAdapter newArrivalAdapter;
    PreQPDetails newArrivalsdetail;
    List<PreQPDetails> newArrivalsdetailArrayList = new ArrayList<>();
    List<String> semarraylist = new ArrayList<>();
    List<String> deptArraylist = new ArrayList<>();
    List<String> subjectArraylist = new ArrayList<>();

    AutoCompleteTextView sub, dep, sem, year;
    String ssub, sdep, ssem, syear;
    Button submit;
    String[] subarray;
    String[] deparray;
    String[] yeararray = {"2000" , "2001" , "2002" , "2003" , "2004" , "2005" , "2006" , "2007" , "2008" , "2009" , "2010" , "2011" , "2012" , "2013" , "2014" , "2015" , "2016" , "2017" , "2018" , "2019" , "2020" , "2021" , "2022" , "2023" , "2024" , "2025"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_previousyear_q_p, container, false);

        recyclerView = view.findViewById(R.id.fspqp_recyclerview);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.fspqp_simpleProgressBar);
        sub=view.findViewById(R.id.fspqp_subject);
        dep=view.findViewById(R.id.fspqp_department);
        sem=view.findViewById(R.id.fspqp_sem);
        year=view.findViewById(R.id.fspqp_year);
        submit=view.findViewById(R.id.fspqp_addbtn);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
//        NewarrivalsAdapter customAdapter = new NewarrivalsAdapter(getContext(), personNames,personImages);
//        recyclerView.setAdapter(customAdapter);
        getsubjects();
        getDepartments();
        getSemesters();
        getyears();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//ssub, sdep, ssem, syear;
                ssub=sub.getText().toString().trim();
                sdep=dep.getText().toString().trim();
                ssem=sem.getText().toString().trim();
                syear=year.getText().toString().trim();


                if (TextUtils.isEmpty(ssub)){
                    sub.setError("Please enter Subject");
                }
                else if (TextUtils.isEmpty(sdep)){
                    dep.setError("Please enter Department");
                }else if (TextUtils.isEmpty(ssem)){
                    sem.setError("Please enter Semester");
                }
                else if (TextUtils.isEmpty(syear)){
                    year.setError("Please enter Year");
                }
                else
                    newarivaldetails();

            }
        });
        return view;
    }

    private void getyears() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.layout_item_autocomplete, R.id.liac_tv_custom, yeararray);

        year.setAdapter(arrayAdapter);
        year.setThreshold(1);
    }


    private void getSemesters() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_sem,
                response -> {
                    Log.v("HELLOSEMESTERS",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                semarraylist.add(jsonArray.getJSONObject(i).getString("tbl_sem_sem"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, semarraylist);
                            sem.setAdapter(arrayAdapter);
                            sem.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void getDepartments() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_department,
                response -> {
                    Log.v("HELLODEPARTMENT",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                deptArraylist.add(jsonArray.getJSONObject(i).getString("tbl_department_dpt"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, deptArraylist);
                            dep.setAdapter(arrayAdapter);
                            dep.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void getsubjects() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_subject,
                response -> {
                    Log.v("HELLOSUBJECT",response);
                    if(response!=null)
                    {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i = 0; i < jsonArray.length(); i++){
                                subjectArraylist.add(jsonArray.getJSONObject(i).getString("tbl_subject_sub"));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.layout_item_autocomplete, R.id.liac_tv_custom, subjectArraylist);
                            sub.setAdapter(arrayAdapter);
                            sub.setThreshold(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show());
        queue.add(stringRequest);
    }

    private void newarivaldetails() {
        simpleProgressBar.setVisibility(View.VISIBLE);
        newArrivalsdetailArrayList.clear();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.view_all_qns,
                response -> {
                    simpleProgressBar.setVisibility(View.INVISIBLE);
                    Log.v("HELLOQUESTION",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            JSONArray jsonArray = jobject.getJSONArray("details");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                newArrivalsdetail = new PreQPDetails();
                                JSONObject jobject1=jsonArray.getJSONObject(i);

                                String qp_id = jobject1.getString("qp_id");
                                String qp_year = jobject1.getString("qp_year");
                                String qp_sem = jobject1.getString("qp_sem");
                                String qp_department = jobject1.getString("qp_department");
                                String qp_subject = jobject1.getString("qp_subject");
                                String qp_questionpaper = jobject1.getString("qp_questionpaper");

                                newArrivalsdetail.setQp_id(qp_id);
                                newArrivalsdetail.setQp_year(qp_year);
                                newArrivalsdetail.setQp_sem(qp_sem);
                                newArrivalsdetail.setQp_department(qp_department);
                                newArrivalsdetail.setQp_subject(qp_subject);
                                newArrivalsdetail.setQp_questionpaper(qp_questionpaper);

                                newArrivalsdetailArrayList.add(newArrivalsdetail);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    newArrivalAdapter = new PreQPAdapter(getContext(), newArrivalsdetailArrayList);
                    recyclerView.setAdapter(newArrivalAdapter);

                },
                error -> {
                    Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("year", syear);
                parmas.put("sem", ssem);
                parmas.put("department", sdep);
                parmas.put("subject", ssub);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }

}