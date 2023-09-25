package com.example.libraryapp.Librarian.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.Librarian.ModelClass.ViewBookModelclass;
import com.example.libraryapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends BaseAdapter {
    private ArrayList<ViewBookModelclass> mArrayList;
    private Context context;
    LayoutInflater inflter;

//    public DataAdapter(@NonNull Context context, @NonNull List<ViewBookModelclass> viewBookModelclassList) {
//        super(context,  viewBookModelclassList);
//        mArrayList = new ArrayList<>(viewBookModelclassList);
//
//    }
//
//    @NonNull
//    @Override
//    public Filter getFilter(){
//        return filter;
//    }
//
//    private Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            FilterResults results = new FilterResults();
//            List<ViewBookModelclass> suggestions = new ArrayList<>();
//
//            if (charSequence==null||charSequence.length()==0){
//                suggestions.addAll(mArrayList);
//            }else {
//                String fiilterPattern = charSequence.toString().toLowerCase().trim();
//                for (ViewBookModelclass bookModelclass:mArrayList){
//                    if (bookModelclass.getBook__id().toLowerCase().contains(fiilterPattern) || bookModelclass.getBook_author().toLowerCase().contains(fiilterPattern) || bookModelclass.getBook_names().toLowerCase().contains(fiilterPattern)|| bookModelclass.getBook_pubisher().toLowerCase().contains(fiilterPattern)) {
//                        suggestions.add(bookModelclass);
//
//                    }
//                }
//            }
//            results.values=suggestions;
//            results.count= suggestions.size();
//            return results;
//
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//            clear();
//            addAll((List)filterResults.values);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            return (((ViewBookModelclass)resultValue).getBook_names());
//        }
//    };




//    private ArrayList<ViewBookModelclass> mFilteredList;

    public DataAdapter(Context context,ArrayList<ViewBookModelclass> arrayList) {
        mArrayList = arrayList;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }

//    @NotNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stud_search_book_card, viewGroup, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//
//        viewHolder.tv_bookid.setText("Book ID : "+mArrayList.get(i).getBook__id());
//        viewHolder.tv_name.setText("Book Name : "+mArrayList.get(i).getBook_names());
//        viewHolder.tv_author.setText("Book Author : "+mArrayList.get(i).getBook_author());
//        viewHolder.tv_publisher.setText("Book Publisher : "+mArrayList.get(i).getBook_pubisher());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mArrayList.size();
//    }

    public void setfilter(List<ViewBookModelclass> actorsList){
        mArrayList=new ArrayList<>();
        mArrayList.addAll(actorsList);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = inflter.inflate(R.layout.stud_search_book_card, null);
        }
//         TextView tv_bookid,tv_name,tv_author,tv_publisher;
//         CardView card;
//        CardView card = (CardView) view.findViewById(R.id.tv_card);
//        TextView tv_bookid = (TextView)view.findViewById(R.id.tv_bookid);
//        TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
//        TextView tv_author = (TextView)view.findViewById(R.id.tv_athor);
//        TextView tv_publisher = (TextView)view.findViewById(R.id.tv_publisher);
//        tv_bookid.setText("Book ID : "+mArrayList.get(i).getBook__id());
//        tv_name.setText("Book Name : "+mArrayList.get(i).getBook_names());
//        tv_author.setText("Book Author : "+mArrayList.get(i).getBook_author());
//        tv_publisher.setText("Book Publisher : "+mArrayList.get(i).getBook_pubisher());
        return view;
    }

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

//    public static class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView tv_bookid,tv_name,tv_author,tv_publisher;
//        private CardView card;
//        public ViewHolder(View view) {
//            super(view);
//            card = (CardView) view.findViewById(R.id.tv_card);
//            tv_bookid = (TextView)view.findViewById(R.id.tv_bookid);
//            tv_name = (TextView)view.findViewById(R.id.tv_name);
//            tv_author = (TextView)view.findViewById(R.id.tv_athor);
//            tv_publisher = (TextView)view.findViewById(R.id.tv_publisher);
//
//        }
//    }

}
