package com.example.libraryapp.Student.ui;

import android.content.Intent;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.libraryapp.Student.Modelclass.Students;
import com.example.libraryapp.Student.StudentHomepage;
import com.example.libraryapp.Student.StudentSharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Myprofile extends Fragment {
    private static final String MY_PREFS_NAME = "StudentsDetails";

TextView stud_name, stud_regno, stud_class, stud_dept, stud_email, stud_phone, stud_dob;
String userid;
LinearLayout linearLayout;
    ProgressBar simpleProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_myprofile, container, false);

//          prefs = getActivity().get (MY_PREFS_NAME, MODE_PRIVATE);
//        userid = prefs.getString("id", "");


        Students students = StudentSharedPrefManager.getInstance1(getContext()).getUser();

        userid=students.getStu_regno();

//        Toast.makeText(getContext(), userid, Toast.LENGTH_SHORT).show();
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.myprofile_simpleProgressBar);

        linearLayout = view.findViewById(R.id.linear2);
        stud_name=view.findViewById(R.id.myprofile_name);
        stud_regno=view.findViewById(R.id.myprofile_regno);
        stud_class=view.findViewById(R.id.myprofile_class);
        stud_dept=view.findViewById(R.id.myprofile_department);
        stud_email=view.findViewById(R.id.myprofile_email);
        stud_phone=view.findViewById(R.id.myprofile_phoneno);
        stud_dob=view.findViewById(R.id.myprofile_dob);


        getdetails();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), StudentHomepage.class);
                startActivity(intent);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        return view;
    }

    private void getdetails() {
        simpleProgressBar.setVisibility(View.VISIBLE);

        {

            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.view_single_student,
                    response -> {
                        simpleProgressBar.setVisibility(View.INVISIBLE);

                        Log.v("HELLOMYPROFILE",response);

                        if(response!=null)
                        {

                            try {
                                linearLayout.setVisibility(View.VISIBLE);
                                JSONObject jobject=new JSONObject(response);
                                JSONArray jsonArray = jobject.getJSONArray("studentdetails");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jobject1=jsonArray.getJSONObject(i);

                                    String stu_id = jobject1.getString("stu_id");
                                    String stu_regno = jobject1.getString("stu_regno");
                                    String stu_name = jobject1.getString("stu_name");
                                    String stu_class = jobject1.getString("stu_class");
                                    String stu_deparment = jobject1.getString("stu_deparment");
                                    String stu_email = jobject1.getString("stu_email");
                                    String stu_mobile = jobject1.getString("stu_mobile");
                                    String stu_dob = jobject1.getString("stu_dob");

//                                id, name, author,publisher, description,remainingnoofcopies;
                                    stud_regno.setText(stu_regno);
                                    stud_name.setText(stu_name);
                                    stud_class.setText(stu_class);
                                    stud_dept.setText(stu_deparment);
                                    stud_dob.setText(stu_dob);
                                    stud_email.setText(stu_email);
                                    stud_phone.setText(stu_mobile);



                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                            }
                        }
//                    newArrivalAdapter = new NewArivalAdap(getContext(), newArrivalsdetailArrayList);
//                    newarivals.setAdapter(newArrivalAdapter);
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
                    parmas.put("regno", userid);

                    return parmas;
                }
            };
            queue.add(stringRequest);

        }

    }
}