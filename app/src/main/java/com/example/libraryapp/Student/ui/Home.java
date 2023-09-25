package com.example.libraryapp.Student.ui;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.libraryapp.Config;
import com.example.libraryapp.HomeActivity;
import com.example.libraryapp.Librarian.Adapters.DataAdapter;
import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.AutocompleteAdapter;
import com.example.libraryapp.Student.Adapters.NewArivalAdap;
import com.example.libraryapp.Student.Modelclass.CategoryDetail;
import com.example.libraryapp.Student.Modelclass.NewArrivalsdetail;
import com.example.libraryapp.Student.StudentHomepage;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {
    private static final String MY_PREFS_NAME = "StudentsDetails";
    String userid;

    RecyclerView newarivals;
    NewArivalAdap newArrivalAdapter;
    NewArrivalsdetail newArrivalsdetail;
    CategoryDetail categoryDetail;
    List<NewArrivalsdetail> newArrivalsdetailArrayList = new ArrayList<>();
    List<CategoryDetail> categoryDetailArrayList = new ArrayList<>();
    TabLayout category_tablayout;
    FrameLayout simpleFrameLayout;
    ProgressBar simpleProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        simpleProgressBar = (ProgressBar) root.findViewById(R.id.newarrival_simpleProgressBar);
//        multiAutoCompleteTextView = root.findViewById(R.id.apbar_search);

        newarivals = root.findViewById(R.id.newarrivalsrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newarivals.setLayoutManager(linearLayoutManager);
        simpleFrameLayout = (FrameLayout) root.findViewById(R.id.subcategory_framelayout);
        category_tablayout = (TabLayout) root.findViewById(R.id.category_tablayout);
        category_tablayout.setTabTextColors(Color.BLUE,Color.RED);

        newarivaldetails();
//        getdetails();

        categorydetaila();
        category_tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i<categoryDetailArrayList.size();i++){
                    Fragment fragment = null;
                    fragment = new CategoryFragment(tab.getPosition()+1+"");
//                            Toast.makeText(MainActivity.this, tab.getPosition(), Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.subcategory_framelayout, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        return root;
    }


    private void categorydetaila() {
        categoryDetailArrayList.clear();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.viewcategories,
                response -> {
                    Log.v("HELLO1",response);

                    if(response!=null)
                    {
                        try {
                            JSONObject jobject=new JSONObject(response);
                            JSONArray jArray=jobject.getJSONArray("categories");
                            for (int i = 0; i < jArray.length(); i++) {
                                categoryDetail = new CategoryDetail();

                                JSONObject jobj=jArray.getJSONObject(i);

                                String siId = jobj.getString("tbl_category_id");
                                String sName = jobj.getString("tbl_category_name");


                                categoryDetail.setId(siId);
                                categoryDetail.setName(sName);

                                categoryDetailArrayList.add(categoryDetail);
                                TabLayout.Tab firstTab = category_tablayout.newTab();
                                firstTab.setText(""+sName+"");

                                category_tablayout.addTab(firstTab);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                error -> {
//                        Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        queue.add(stringRequest);

    }

    private void newarivaldetails() {
        newArrivalsdetailArrayList.clear();
        simpleProgressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.viewnewarrivals,
                response -> {
                    Log.v("HELLO",response);
                    simpleProgressBar.setVisibility(View.INVISIBLE);

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
                    newarivals.setAdapter(newArrivalAdapter);
                },
                error -> {
                    Toast.makeText(getContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });
        queue.add(stringRequest);

    }
}