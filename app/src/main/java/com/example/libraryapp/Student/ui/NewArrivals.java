package com.example.libraryapp.Student.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.HomeActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.NewArivalAdap;
import com.example.libraryapp.Student.Adapters.NewarrivalsAdapter;
import com.example.libraryapp.Student.Modelclass.CategoryDetail;
import com.example.libraryapp.Student.Modelclass.NewArrivalsdetail;
import com.example.libraryapp.Student.StudentHomepage;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewArrivals extends Fragment {

    RecyclerView recyclerView;
    private static final String MY_PREFS_NAME = "StudentsDetails";
    String userid;

    ProgressBar simpleProgressBar;

    NewArivalAdap newArrivalAdapter;
    NewArrivalsdetail newArrivalsdetail;
    List<NewArrivalsdetail> newArrivalsdetailArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newarrivals, container, false);
        simpleProgressBar = (ProgressBar) root.findViewById(R.id.newarrv_simpleProgressBar);

        recyclerView = root.findViewById(R.id.newarrivalfragment_recycler);
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

        newArrivalsdetailArrayList.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.viewnewarrivals,
                response -> {
                    simpleProgressBar.setVisibility(View.INVISIBLE);

                    Log.v("HELLO",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            JSONArray jsonArray = jobject.getJSONArray("details");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                newArrivalsdetail = new NewArrivalsdetail();
                                JSONObject jobject1=jsonArray.getJSONObject(i);

                                String ID = jobject1.getString("book__id");
                                String ImagePath = jobject1.getString("book_image");
                                String name = jobject1.getString("book_names");
                                String author = jobject1.getString("book_author");

                                newArrivalsdetail.setBookid(ID);
                                newArrivalsdetail.setImage(ImagePath);
                                newArrivalsdetail.setName(name);
                                newArrivalsdetail.setAuthor(author);


                                newArrivalsdetailArrayList.add(newArrivalsdetail);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    newArrivalAdapter = new NewArivalAdap(getContext(), newArrivalsdetailArrayList);
                    recyclerView.setAdapter(newArrivalAdapter);
                },
                error -> {
                    Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        queue.add(stringRequest);

    }


}