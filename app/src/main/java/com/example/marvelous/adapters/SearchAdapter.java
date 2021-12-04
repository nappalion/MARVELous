package com.example.marvelous.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marvelous.R;
import com.example.marvelous.activities.ComicDetailActivity;
import com.example.marvelous.activities.SettingsActivity;
import com.example.marvelous.models.Comic;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Comic> comics;
    List<Comic> comicsAll;

    // Pass in context and list of tweets
    public SearchAdapter(Context context, List<Comic> comics) {
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
        Comic comic = comics.get(position);
        holder.bind(comic);
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
    public void addAll(List<Comic> list) {
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

            List<Comic> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(comicsAll);
            }
            else{
                for(Comic comic: comicsAll){
                    if(comic.title.toLowerCase().contains(charSequence.toString().toLowerCase())){
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
            comics.addAll((Collection<? extends Comic>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvComicname;
        TextView tvDescription;
        RelativeLayout comicCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvComicname = itemView.findViewById(R.id.tvComicname);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            comicCard = itemView.findViewById(R.id.comicCard);
        }

        public void bind(Comic comic) {
            tvComicname.setText(comic.title);

            if (comic.series == null){
                tvDescription.setText("No Description");
            }
            else{
                tvDescription.setText(comic.series);
            }
             Glide.with(context).load(comic.url).into(ivImage);

            comicCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ComicDetailActivity.class);
                    intent.putExtra("comic", Parcels.wrap(comic));
                    context.startActivity(intent);
                }
            });

        }
    }

}
