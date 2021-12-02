package com.example.marvelous.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.models.Comic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    Context context;
    List<String> comics;
    List<String> comicsAll;

    // Pass in context and list of tweets
    public SearchAdapter(Context context, List<String> comics) {
        this.context = context;
        this.comics = comics;
        this.comicsAll = new ArrayList<>(comics);
    }
    // For each row, inflate a layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDescription.setText(String.valueOf(position));
        holder.tvComicname.setText(comics.get(position));
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        comics.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<String> list) {
        comics.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(comicsAll);
            }
            else{
                for(String comic: comicsAll){
                    if(comic.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(comic);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        // ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            comics.clear();
            comics.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvComicname;
        TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvComicname = itemView.findViewById(R.id.tvComicname);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(String comic) {
            tvComicname.setText(comic);
//            Glide.with(context).load(tweet.user.profileImageUrl).into(ivImage);
        }
    }

}
