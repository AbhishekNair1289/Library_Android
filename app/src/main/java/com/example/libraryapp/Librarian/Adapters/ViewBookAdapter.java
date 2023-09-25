package com.example.libraryapp.Librarian.Adapters;

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
import com.example.libraryapp.Librarian.ModelClass.UVBookstaken;
import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.BookDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewBookAdapter extends RecyclerView.Adapter<ViewBookAdapter.MyViewHolder> {
    Context context;
    List<ViewBookModelclass> viewBookModelclasses;

    public ViewBookAdapter(Context context, List<ViewBookModelclass> viewBookModelclasses) {
        this.context = context;
        this.viewBookModelclasses = viewBookModelclasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewbookrecyclercardview, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(viewBookModelclasses.get(position).getBook_names());
        holder.id.setText("Book ID : " +viewBookModelclasses.get(position).getBook__id());
        holder.author.setText(viewBookModelclasses.get(position).getBook_author());
        String imageurl = viewBookModelclasses.get(position).getBook_image();

        Picasso.with(context)
                .load(Config.imgbase_url+imageurl)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookDetail.class);
                String id = viewBookModelclasses.get(position).getBook__id();
                intent.putExtra("BookID", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewBookModelclasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView id, name, author;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.vbrc_bookimage);
            id = itemView.findViewById(R.id.vbrc_bookid);
            name = itemView.findViewById(R.id.vbrc_bookname);
            author = itemView.findViewById(R.id.vbrc_bookauthor);
            cardView = itemView.findViewById(R.id.vbrc_card);
        }
    }
}
