package com.example.libraryapp.Librarian.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.LibrarianLogin;
import com.example.libraryapp.Librarian.LibrarianSharedPrefManager;
import com.example.libraryapp.Librarian.LibraryHomeActivity;
import com.example.libraryapp.Librarian.ModelClass.Librarian;
import com.example.libraryapp.Librarian.ModelClass.ListtosendEmail;
import com.example.libraryapp.Librarian.ModelClass.UVBookstaken;
import com.example.libraryapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListtosendEmailAdapter extends RecyclerView.Adapter<ListtosendEmailAdapter.MyViewHolder>{

    Context context;
    ArrayList<ListtosendEmail> listtosendEmailList;

    public ListtosendEmailAdapter(Context context, ArrayList<ListtosendEmail> listtosendEmailList) {
        this.context = context;
        this.listtosendEmailList = listtosendEmailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtosendemailcardview, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.slno.setText(listtosendEmailList.get(position).getSlno());
        holder.stuid.setText(listtosendEmailList.get(position).getStuid());
        holder.stuname.setText(listtosendEmailList.get(position).getStuname());
        holder.bookid.setText(listtosendEmailList.get(position).getBookid());
        holder.bookname.setText(listtosendEmailList.get(position).getBookname());
        holder.authername.setText(listtosendEmailList.get(position).getAuthername());
        holder.issuedate.setText(listtosendEmailList.get(position).getIssuedate());
        holder.issuestatus.setText(listtosendEmailList.get(position).getIssuestatus());
        holder.sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendemail(listtosendEmailList.get(position).getStuid());
            }
        });
//        Toast.makeText(context, listtosendEmailList.get(position).getSlno(), Toast.LENGTH_SHORT).show();
    }

    private void sendemail(String stuid) {
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.sendemail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response", response);
                String error = "";
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        error = jsonObject.getString("error");
                        if (error.contains("true")) {
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (error.contains("false")) {
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("std_id", stuid);

                return parmas;
            }
        };
        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, listtosendEmailList.size(), Toast.LENGTH_SHORT).show();
        return listtosendEmailList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slno, stuid, stuname, bookid, bookname, authername, issuedate, issuestatus;
        Button sendemail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            slno=itemView.findViewById(R.id.lsec_sno);
            stuid=itemView.findViewById(R.id.lsec_stuid);
            stuname=itemView.findViewById(R.id.lsec_stuname);
            bookid=itemView.findViewById(R.id.lsec_bookid);
            bookname=itemView.findViewById(R.id.lsec_bookname);
            authername=itemView.findViewById(R.id.lsec_authorname);
            issuedate=itemView.findViewById(R.id.lsec_issuedate);
            issuestatus=itemView.findViewById(R.id.lsec_returnstatus);
            sendemail=itemView.findViewById(R.id.lsec_sendemailbtn);
        }
    }
}
