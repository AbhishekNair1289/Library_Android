package com.example.libraryapp.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;
import com.example.libraryapp.Student.Modelclass.Bookstaken;


import java.util.List;

public class BooksTakenAdapter extends RecyclerView.Adapter<BooksTakenAdapter.MyViewHolder> {

    Context context;
    List<Bookstaken> bookstakenListo;

    public BooksTakenAdapter(Context context, List<Bookstaken> bookstakenArrayList) {

        this.context=context;
        this.bookstakenListo=bookstakenArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookstakencardview, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookname.setText(bookstakenListo.get(position).getBookname());
        holder.SNo.setText(bookstakenListo.get(position).getSNo());
        holder.bookid.setText(bookstakenListo.get(position).getBookid());
        holder.authorname.setText(bookstakenListo.get(position).getAuthorname());
        holder.issuedate.setText(bookstakenListo.get(position).getIssuedate());
        holder.returndate.setText(bookstakenListo.get(position).getReturndate());
        holder.returnstatus.setText(bookstakenListo.get(position).getReturnstatus());

    }

    @Override
    public int getItemCount() {
        return bookstakenListo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView SNo, bookid, bookname, authorname, issuedate, returndate, returnstatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SNo=itemView.findViewById(R.id.btc_sno);
            bookid=itemView.findViewById(R.id.btc_bookid);
            bookname=itemView.findViewById(R.id.btc_bookname);
            authorname=itemView.findViewById(R.id.btc_authorname);
            issuedate=itemView.findViewById(R.id.btc_issuedate);
            returndate=itemView.findViewById(R.id.btc_returndate);
            returnstatus=itemView.findViewById(R.id.btc_returnstatus);
        }
    }
}
