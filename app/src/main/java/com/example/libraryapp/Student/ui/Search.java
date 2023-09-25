package com.example.libraryapp.Student.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.Adapters.DataAdapter;
import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.AutocompleteAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Search extends AppCompatActivity {

//        DataAdapter viewBookAdapter;
    AutocompleteAdapter autocompleteAdapter;
    //    ListView recyclerView;
    EditText searchView;
    ViewBookModelclass viewBookModelclass;
    ArrayList<ViewBookModelclass> viewBookModelclassList  = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        recyclerView = findViewById(R.id.studenthome_list);
        searchView = findViewById(R.id.search_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        getdetails();

        searchView.addTextChangedListener(new TextWatcher() {
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
viewBookModelclassList.clear();
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
                    }
                    autocompleteAdapter = new AutocompleteAdapter(getApplicationContext(), viewBookModelclassList);
                    recyclerView.setAdapter(autocompleteAdapter);

//                    viewBookAdapter = new DataAdapter( getApplicationContext(),viewBookModelclassList);
//                    recyclerView.setAdapter(viewBookAdapter);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                })

                ;
        queue.add(stringRequest);
    }

}