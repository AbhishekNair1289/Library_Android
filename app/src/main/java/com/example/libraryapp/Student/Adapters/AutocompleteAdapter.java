package com.example.libraryapp.Student.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Config;
import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;
import com.example.libraryapp.Student.BookDetail;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends RecyclerView.Adapter<AutocompleteAdapter.ViewHolder> implements Filterable {

    private final ArrayList<ViewBookModelclass> mArrayList;
    private final ArrayList<ViewBookModelclass> mfilterarraylist ;
    private Context context;



    public AutocompleteAdapter(@NonNull Context context,  List<ViewBookModelclass> viewBookModelclassList) {
        mArrayList = new ArrayList<>(viewBookModelclassList);
        this.context=context;
        mfilterarraylist = new ArrayList<>(viewBookModelclassList);
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stud_search_book_card, viewGroup, false);
        return new ViewHolder(view);
    }
    //
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_bookid.setText("Book ID : "+mArrayList.get(i).getBook__id());
        viewHolder.tv_name.setText("Book Name : "+mArrayList.get(i).getBook_names());
        viewHolder.tv_author.setText("Book Author : "+mArrayList.get(i).getBook_author());
        viewHolder.tv_publisher.setText("Book Publisher : "+mArrayList.get(i).getBook_pubisher());
        String imageurl = mArrayList.get(i).getBook_image();

        Picasso.with(context)
                .load(Config.imgbase_url+imageurl)
                .fit()
                .centerCrop()
                .into(viewHolder.imageView);
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookDetail.class);
                String id = mArrayList.get(i).getBook__id();
                intent.putExtra("BookID", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_bookid,tv_name,tv_author,tv_publisher;
        private CardView card;
        private ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView=(ImageView)view.findViewById(R.id.tv_bookimage);
            card = (CardView) view.findViewById(R.id.tv_card);
            tv_bookid = (TextView)view.findViewById(R.id.tv_bookid);
            tv_name = (TextView)view.findViewById(R.id.tv_bookname);
            tv_author = (TextView)view.findViewById(R.id.tv_bookauthor);
            tv_publisher = (TextView)view.findViewById(R.id.tv_bookpublisher);

        }
    }

//    @NonNull
    @Override
    public Filter getFilter(){
        return filter;
    }
//
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ViewBookModelclass> suggestions = new ArrayList<>();

            if (charSequence==null||charSequence.length()==0){
                suggestions.addAll(mfilterarraylist);
            }else {
                String fiilterPattern = charSequence.toString().toLowerCase().trim();
                for (ViewBookModelclass bookModelclass:mfilterarraylist){
                    if (bookModelclass.getBook__id().toLowerCase().contains(fiilterPattern) || bookModelclass.getBook_author().toLowerCase().contains(fiilterPattern) || bookModelclass.getBook_names().toLowerCase().contains(fiilterPattern)|| bookModelclass.getBook_pubisher().toLowerCase().contains(fiilterPattern)) {
//                     if (bookModelclass.getBook_names().toLowerCase().contains(fiilterPattern)) {
                        suggestions.add(bookModelclass);

                    }
                }
            }
            FilterResults results = new FilterResults();

            results.values=suggestions;
            results.count= suggestions.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mArrayList.clear();
            mArrayList.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return (((ViewBookModelclass)resultValue).getBook_names());
        }
    };




//    private ArrayList<ViewBookModelclass> mFilteredList;

//        public DataAdapter(Context context,ArrayList<ViewBookModelclass> arrayList) {
//            mArrayList = arrayList;
//            this.context = context;
//            inflter = (LayoutInflater.from(context));
//        }



//        public void setfilter(List<ViewBookModelclass> actorsList){
//            mArrayList=new ArrayList<>();
//            mArrayList.addAll(actorsList);
//            notifyDataSetChanged();
//
//        }
//
//        @Override
//        public int getCount() {
//            return mArrayList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }

//    @NonNull
//        @Override
//        public View getView(int i,@NonNull View view,@NonNull ViewGroup viewGroup) {
//            if (view==null){
//                view = LayoutInflater.from(getContext()).inflate(R.layout.stud_search_book_card,viewGroup,  false);
//            }
////         TextView tv_bookid,tv_name,tv_author,tv_publisher;
////         CardView card;
//            CardView card = (CardView) view.findViewById(R.id.tv_card);
//            TextView tv_bookid = (TextView)view.findViewById(R.id.tv_bookid);
//            TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
//            TextView tv_author = (TextView)view.findViewById(R.id.tv_athor);
//            TextView tv_publisher = (TextView)view.findViewById(R.id.tv_publisher);
//
//            tv_bookid.setText("Book ID : "+mArrayList.get(i).getBook__id());
//            tv_name.setText("Book Name : "+mArrayList.get(i).getBook_names());
//            tv_author.setText("Book Author : "+mArrayList.get(i).getBook_author());
//            tv_publisher.setText("Book Publisher : "+mArrayList.get(i).getBook_pubisher());
//            return  view;
//        }

//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//
//                    mFilteredList = mArrayList;
//                } else {
//
//                    ArrayList<ViewBookModelclass> filteredList = new ArrayList<>();
//
//                    for (ViewBookModelclass androidVersion : mArrayList) {
//
//                        if (androidVersion.getBook__id().toLowerCase().contains(charString) || androidVersion.getBook_author().toLowerCase().contains(charString) || androidVersion.getBook_names().toLowerCase().contains(charString)|| androidVersion.getBook_pubisher().toLowerCase().contains(charString)) {
//
//                            filteredList.add(androidVersion);
//                        }
//                    }
//
//                    mFilteredList = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilteredList = (ArrayList<ViewBookModelclass>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }





}
