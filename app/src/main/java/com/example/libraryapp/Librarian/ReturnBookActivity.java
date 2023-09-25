package com.example.libraryapp.Librarian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReturnBookActivity extends AppCompatActivity {

    String issueid, bookid, issuedate, sfine;
    String currentDateandTime;
    EditText bookidedt, issuedateedt, returndateedt, fine;
    Button returnbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        issueid=getIntent().getExtras().getString("issue_id");
        bookid=getIntent().getExtras().getString("issue_bookid");
        issuedate=getIntent().getExtras().getString("bookissuedate");

        issuedateedt=findViewById(R.id.arb_issuedate);
        returndateedt=findViewById(R.id.arb_returndate);
        returnbtn=findViewById(R.id.arb_addbtn);
        issuedateedt.setText(issuedate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         currentDateandTime = sdf.format(new Date());
         returndateedt.setText(currentDateandTime);

         returnbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 senddata();
             }
         });

    }

    private void senddata() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.returnbook,
                response -> {
                    Log.v("HELLO987456",response);

                    if(response!=null)
                    {

                        try {
                            JSONObject jobject=new JSONObject(response);
                            String error = jobject.getString("error");
                            String msg = jobject.getString("message");
                            String fine = jobject.getString("Fine");
                            if(error.equals("false"))
                            {
                                if (!fine.equals("0")) {
                                    new AlertDialog.Builder(this)
                                            .setTitle("You have fine for this book!!!")
                                            .setMessage("You have to pay ₹" + fine)
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    Intent intent=new Intent(getApplicationContext(), ViewUserActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).create().show();
                                }
                                else {
                                    new AlertDialog.Builder(this)
                                            .setTitle("Good!!! You have submitted book before due")
                                            .setMessage("You have no  fine for this book!!!")
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    Intent intent=new Intent(getApplicationContext(), ViewUserActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).create().show();

                                }
                           }

                            else
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No Data123", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("Id", issueid);

                return parmas;
            }
        };
        queue.add(stringRequest);

    }

//    private void updatecopies() {
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updateissue,
//                response -> {
//                    Log.v("HELLO",response);
//
//                    if(response!=null)
//                    {
//
//                        try {
//                            JSONObject jobject=new JSONObject(response);
//                            String error = jobject.getString("error");
//                            String msg = jobject.getString("message");
//                            if(error.equals("false"))
//                            {
//                                Toast.makeText(this, "Book Return Successful", Toast.LENGTH_SHORT).show();
////                                updatecopies();

//                            }
//                            else
//                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                error -> {
//                    Toast.makeText(getApplicationContext(), "Error response", Toast.LENGTH_SHORT).show();
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> parmas = new HashMap<>();
//
//                //here we pass params
//                parmas.put("Id", bookid);
////                parmas.put("y-m-d",currentDateandTime);
//                return parmas;
//            }
//        };
//        queue.add(stringRequest);
//
//    }
}