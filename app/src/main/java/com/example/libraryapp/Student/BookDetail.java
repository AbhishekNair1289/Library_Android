package com.example.libraryapp.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookDetail extends AppCompatActivity {
    ImageView imageView;
    TextView id, name, author,publisher, description,remainingnoofcopies;

    String BOOKID;
    ProgressBar simpleProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        BOOKID=getIntent().getExtras().getString("BookID");
        simpleProgressBar = (ProgressBar) findViewById(R.id.abd_simpleProgressBar);

        imageView=findViewById(R.id.abd_bookimage);
        id=findViewById(R.id.abd_bookid);
        name=findViewById(R.id.abd_bookname);
        author=findViewById(R.id.abd_bookauthor);
        description=findViewById(R.id.abd_bookdescription);
        remainingnoofcopies=findViewById(R.id.abd_noofcopies);
        publisher=findViewById(R.id.abd_bookpublisher);
        getdetails();

    }

    private void getdetails() {
        simpleProgressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.viewsinglebookdetails,
                response -> {
                    Log.v("HELLOPOIUYTR",response);
                    simpleProgressBar.setVisibility(View.INVISIBLE);

                    if(response!=null)
                    {

                        try {

                            imageView.setVisibility(View.VISIBLE);
                            id.setVisibility(View.VISIBLE);
                            remainingnoofcopies.setVisibility(View.VISIBLE);
                            name.setVisibility(View.VISIBLE);
                            author.setVisibility(View.VISIBLE);
                            publisher.setVisibility(View.VISIBLE);
                            description.setVisibility(View.VISIBLE);


                            JSONObject jobject=new JSONObject(response);
                            JSONArray jsonArray = jobject.getJSONArray("details");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobject1=jsonArray.getJSONObject(i);

                                String book__id = jobject1.getString("book__id");
                                String book_image = jobject1.getString("book_image");
                                String book_names = jobject1.getString("book_names");
                                String book_author = jobject1.getString("book_author");
                                String book_pubisher = jobject1.getString("book_pubisher");
                                String book_description = jobject1.getString("book_description");
                                String book_no_of_copies = jobject1.getString("book_no_of_copies");
                                String book_remainingcopies = jobject1.getString("book_remainingcopies");

//                                id, name, author,publisher, description,remainingnoofcopies;
                                id.setText("Book ID : "+book__id);
                                remainingnoofcopies.setText("Remaining No of Copies : "+book_remainingcopies);
                                name.setText(book_names);
                                author.setText("Author : "+book_author);
                                publisher.setText("Publisher : "+book_pubisher);
                                description.setText(book_description);



                                Picasso.with(getApplicationContext())
                                        .load(Config.imgbase_url+book_image)
                                        .fit()
                                        .centerCrop()
                                        .into(imageView);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
//                    newArrivalAdapter = new NewArivalAdap(getContext(), newArrivalsdetailArrayList);
//                    newarivals.setAdapter(newArrivalAdapter);
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("id", BOOKID);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }
}