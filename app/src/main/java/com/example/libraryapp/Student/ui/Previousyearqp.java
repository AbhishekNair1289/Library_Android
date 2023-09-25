package com.example.libraryapp.Student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.NewArivalAdap;
import com.example.libraryapp.Student.Adapters.PreQPAdapter;
import com.example.libraryapp.Student.Modelclass.NewArrivalsdetail;
import com.example.libraryapp.Student.Modelclass.PreQPDetails;
import com.example.libraryapp.Student.StudentHomepage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Previousyearqp extends Fragment {

    RecyclerView recyclerView;
    private static final String MY_PREFS_NAME = "StudentsDetails";
    String userid;

    ProgressBar simpleProgressBar;

    PreQPAdapter newArrivalAdapter;
    PreQPDetails newArrivalsdetail;
    List<PreQPDetails> newArrivalsdetailArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_previousqp, container, false);
        recyclerView = root.findViewById(R.id.questionpaper_recyclerview);
        simpleProgressBar = (ProgressBar) root.findViewById(R.id.quespaper_simpleProgressBar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
//        NewarrivalsAdapter customAdapter = new NewarrivalsAdapter(getContext(), personNames,personImages);
//        recyclerView.setAdapter(customAdapter);
        newarivaldetails();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), StudentHomepage.class);
                startActivity(intent);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        return root;
    }
    private void newarivaldetails() {
        simpleProgressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.view_all_qns,
                response -> {
                    simpleProgressBar.setVisibility(View.INVISIBLE);
                    Log.v("HELLO",response);

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
                });
        queue.add(stringRequest);

    }

}