package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.libraryapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateBookActivity extends AppCompatActivity {

    EditText searchbookid;
    ImageView searchbtn;
    TextView status, name;
    String BOOKID;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        name=findViewById(R.id.aub_bookname);
        status=findViewById(R.id.aub_bookstatus);
        searchbookid=findViewById(R.id.aub_searchbookedt);
        searchbtn=findViewById(R.id.aub_searchbtn);
        update=findViewById(R.id.aub__updatebtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BOOKID=searchbookid.getText().toString();
                getdetails();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 updatedata();
            }
        });


    }

    private void updatedata() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updatebook,
                response -> {
                    Log.v("HELLO",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            String error = jobject.getString("error");
                            String msg = jobject.getString("message");
                            if(error.equals("false"))
                            {
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


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
                parmas.put("Id", BOOKID);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }

    private void getdetails() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.viewsinglebookdetails,
                response -> {
                    Log.v("HELLO",response);

                    if(response!=null)
                    {

                        try {
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
                                String book_status = jobject1.getString("book_status");

//                                id, name, author,publisher, description,remainingnoofcopies;

                                name.setText(book_names);
                                status.setText(book_status);




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