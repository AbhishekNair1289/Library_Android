package com.example.libraryapp.Student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.provider.SyncStateContract;
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
import com.example.libraryapp.Student.Modelclass.NewArrivalsdetail;
import com.example.libraryapp.Student.Modelclass.PreQPDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreQPAdapter  extends RecyclerView.Adapter<PreQPAdapter.MyViewHolder>{
    Context context;
    List<PreQPDetails> arrivalsdetailList;

    public PreQPAdapter(Context context, List<PreQPDetails> arrivalsdetailList1) {
        this.context = context;
        this.arrivalsdetailList = arrivalsdetailList1;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_cardview, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.name.setText(arrivalsdetailList.get(position).getQp_subject());
        holder.pdf.setText(arrivalsdetailList.get(position).getQp_questionpaper());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreQPDetails pdf = (PreQPDetails) arrivalsdetailList.get(position);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setData(Uri.parse(Config.pdfbase_url+pdf.getQp_questionpaper()));
//                context.startActivity(intent);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(Config.pdfbase_url+arrivalsdetailList.get(position).getQp_questionpaper()), "application/pdf");

                Intent chooser = Intent.createChooser(browserIntent,"Open pdf");
//                chooser.setAction(Intent.ACTION_VIEW);
//                chooser.setData(Uri.parse(Config.pdfbase_url+pdf.getQp_questionpaper()));
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional

                context.startActivity(chooser);
            }
        });

//        String imageurl = arrivalsdetailList.get(position).getImage();
//
//        Picasso.with(context)
//                .load(Config.imgbase_url+imageurl)
//                .fit()
//                .centerCrop()
//                .into(holder.imageView);

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, BookDetail.class);
//                String id = arrivalsdetailList.get(position).getQp_id();
//                intent.putExtra("BookID", id);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrivalsdetailList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pdf, name;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pdf = itemView.findViewById(R.id.qcv_pdf);
//            name = itemView.findViewById(R.id.qcv_pdfname);
            cardView = itemView.findViewById(R.id.qcv_card);
        }
    }
}
