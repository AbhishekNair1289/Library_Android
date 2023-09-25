package com.example.libraryapp.Student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Config;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.BookDetail;
import com.example.libraryapp.Student.Modelclass.Categorybookdetails;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryDetailAdapter extends RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolder>{

    Context context;
    List<Categorybookdetails> arrivalsdetailList;
    public CategoryDetailAdapter(Context context, List<Categorybookdetails> arrivalsdetailList1) {
        this.context = context;
        this.arrivalsdetailList = arrivalsdetailList1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistbycategory, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(arrivalsdetailList.get(position).getName());
        holder.id.setText("Book ID : " +arrivalsdetailList.get(position).getBookid());
        holder.author.setText(arrivalsdetailList.get(position).getAuthor());
        String imageurl = arrivalsdetailList.get(position).getImage();

        Picasso.with(context)
                .load(Config.imgbase_url+imageurl)
                .fit()
                .centerCrop()
                .into(holder.imageView);
//        Glide.with(context)
//                .load(Config.imgbase_url+imageurl)
//                // .placeholder(R.drawable.cereal)
//                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookDetail.class);
                String id = arrivalsdetailList.get(position).getBookid();
                intent.putExtra("BookID", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrivalsdetailList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView id, name, author;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.blc_bookimage);
            id = itemView.findViewById(R.id.blc_bookid);
            name = itemView.findViewById(R.id.blc_bookname);
            author = itemView.findViewById(R.id.blc_bookauthor);
            cardView = itemView.findViewById(R.id.blc_card);
        }
    }
}
