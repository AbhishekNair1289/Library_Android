package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.libraryapp.Librarian.Adapters.ViewBookAdapter;
import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.AutocompleteAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewBookActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView;
    TextView id, name, author,publisher, description,remainingnoofcopies, totalnoofcopies;
    EditText searchbookid;
    ImageView searchbtn;
    ViewBookModelclass viewBookModelclass = new ViewBookModelclass();
    ArrayList<ViewBookModelclass> viewBookModelclassList;
    ViewBookAdapter viewBookAdapter;
    RecyclerView recyclerView;
    String BOOKID;
    AutocompleteAdapter autocompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        recyclerView=findViewById(R.id.avb_recyclerview);

        searchbookid=findViewById(R.id.avb_searchbookedt);
        searchbtn=findViewById(R.id.avb_searchbtn);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.avb_swiperecycler);
        swipeRefreshLayout.setOnRefreshListener(this::getdetails);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
//        getdetails();
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getdetails();
            }
        });

        searchbookid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                autocompleteAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void getdetails() {
//        String BOOKID = searchbook.getText().toString();
        viewBookModelclassList=new ArrayList<>();

        viewBookModelclassList.clear();
        swipeRefreshLayout.setRefreshing(true);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.view_all_books,
                response -> {
                    Log.v("HELLOALLBOOKS",response);

                    if(response!=null)
                    {

                        try {

                            JSONObject jobject=new JSONObject(response);
                            JSONArray jsonArray = jobject.getJSONArray("bookdetails");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                viewBookModelclass = new ViewBookModelclass();

                                JSONObject jobject1=jsonArray.getJSONObject(i);

                                String book__id = jobject1.getString("book__id");
                                String book_image = jobject1.getString("book_image");
                                String book_names = jobject1.getString("book_names");
                                String book_author = jobject1.getString("book_author");
                                String book_pubisher = jobject1.getString("book_pubisher");
                                String book_description = jobject1.getString("book_description");
                                String book_no_of_copies = jobject1.getString("book_no_of_copies");
                                String book_remainingcopies = jobject1.getString("book_remainingcopies");

                                viewBookModelclass.setBook__id(book__id);
                                viewBookModelclass.setBook_image(book_image);
                                viewBookModelclass.setBook_names(book_names);
                                viewBookModelclass.setBook_author(book_author);
                                viewBookModelclass.setBook_pubisher(book_pubisher);
                                viewBookModelclass.setBook_description(book_description);
                                viewBookModelclass.setBook_no_of_copies(book_no_of_copies);
                                viewBookModelclass.setBook_remainingcopies(book_remainingcopies);

                                viewBookModelclassList.add(viewBookModelclass);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    autocompleteAdapter = new AutocompleteAdapter(getApplicationContext(), viewBookModelclassList);
                    recyclerView.setAdapter(autocompleteAdapter);
                    autocompleteAdapter.notifyDataSetChanged();
//                    viewBookAdapter = new DataAdapter( getApplicationContext(),viewBookModelclassList);
//                    recyclerView.setAdapter(viewBookAdapter);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                });
        queue.add(stringRequest);

    }

}