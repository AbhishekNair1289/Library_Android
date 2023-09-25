package com.example.libraryapp.Student.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.CategoryDetailAdapter;
import com.example.libraryapp.Student.Modelclass.Categorybookdetails;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {
    String id;
    Categorybookdetails categorybookdetails;
    List<Categorybookdetails> categorybookdetailsArrayList = new ArrayList<>();
    CategoryDetailAdapter categoryDetailAdapter;
    RecyclerView gridView;
    TextView mainviewAll;
    ProgressBar simpleProgressBar;

    @SuppressLint("ValidFragment")
    public CategoryFragment(String position) {
        id=position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        simpleProgressBar = (ProgressBar) view.findViewById(R.id.category_simpleProgressBar);

        gridView = (RecyclerView) view.findViewById(R.id.categoryrecyclerview); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        getData();

        return  view;
    }
    private void getData() {
        simpleProgressBar.setVisibility(View.VISIBLE);
        categorybookdetailsArrayList.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.view_book_bycat,
                response ->
                {
                    Log.v("HELLO1",response);
                    simpleProgressBar.setVisibility(View.INVISIBLE);

                    if(response!=null)
                    {
                        try {

                            JSONObject jobject=new JSONObject(response);
                            if (jobject.getString("error").equals("true")){
                                Toast.makeText(getContext(), "No Data to show", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                JSONArray jArray=jobject.getJSONArray("booksbycategory");
                                for (int i = 0; i < jArray.length(); i++) {
                                    categorybookdetails = new Categorybookdetails();

                                    JSONObject jobj=jArray.getJSONObject(i);

                                    String book__id = jobj.getString("book__id");
                                    String book_image = jobj.getString("book_image");
                                    String book_names = jobj.getString("book_names");
                                    String book_author = jobj.getString("book_author");

                                    categorybookdetails.setBookid(book__id);
                                    categorybookdetails.setImage(book_image);
                                    categorybookdetails.setName(book_names);
                                    categorybookdetails.setAuthor(book_author);

                                    categorybookdetailsArrayList.add(categorybookdetails);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    categoryDetailAdapter = new CategoryDetailAdapter(getContext(), categorybookdetailsArrayList);
                    gridView.setAdapter(categoryDetailAdapter);

                },
                error -> {
                    Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("catid", id);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }

}