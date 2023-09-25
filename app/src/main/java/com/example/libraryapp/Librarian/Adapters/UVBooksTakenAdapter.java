package com.example.libraryapp.Librarian.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Librarian.ModelClass.UVBookstaken;
import com.example.libraryapp.Librarian.ReturnBookActivity;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.Adapters.BooksTakenAdapter;
import com.example.libraryapp.Student.Modelclass.Bookstaken;

import java.util.List;

public class UVBooksTakenAdapter extends RecyclerView.Adapter<UVBooksTakenAdapter.MyViewHolder> {

    Context context;
    List<UVBookstaken> bookstakenListo;

    public UVBooksTakenAdapter(Context context, List<UVBookstaken> bookstakenListo) {
        this.context = context;
        this.bookstakenListo = bookstakenListo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.uvbookstakencardview, null);
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
        if (bookstakenListo.get(position).getReturnstatus().equals("Not Returned")){
            holder.returnbook.setVisibility(View.VISIBLE);
            holder.returnbook.setOnClickListener(view -> {

                String id = bookstakenListo.get(position).getIssue_id();
                String bookid = bookstakenListo.get(position).getBookid();
                String issuedate = bookstakenListo.get(position).getIssuedate();


                Intent intent = new Intent(context, ReturnBookActivity.class);

                intent.putExtra("issue_id", id);
                intent.putExtra("issue_bookid", bookid);
                intent.putExtra("bookissuedate", issuedate);

//            intent.putExtra("issue_id", id);
//            intent.putExtra("issue_id", id);
//            intent.putExtra("issue_id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }
    }



    @Override
    public int getItemCount() {
        return bookstakenListo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView SNo, bookid, bookname, authorname, issuedate, returndate, returnstatus;
        Button returnbook;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SNo=itemView.findViewById(R.id.uvbtc_sno);
            bookid=itemView.findViewById(R.id.uvbtc_bookid);
            bookname=itemView.findViewById(R.id.uvbtc_bookname);
            authorname=itemView.findViewById(R.id.uvbtc_authorname);
            issuedate=itemView.findViewById(R.id.uvbtc_issuedate);
            returndate=itemView.findViewById(R.id.uvbtc_returndate);
            returnstatus=itemView.findViewById(R.id.uvbtc_returnstatus);
            returnbook=itemView.findViewById(R.id.uvbtc_returnbtn);

        }
    }
}
