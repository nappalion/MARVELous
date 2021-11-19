package com.example.marvelous.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.models.UserComic;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<String> mPossStatuses;

    public LibraryAdapter(List<String> possStatus) {
        this.mPossStatuses = possStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View libraryView = inflater.inflate(R.layout.item_library, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(libraryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data model based on position
        String possStatus = mPossStatuses.get(position);

        // set item views based on views in data model
        holder.bind(possStatus);
    }

    @Override
    public int getItemCount() {
        return mPossStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStatus;
        public RecyclerView rvComics;
        public ImageView ivArrow;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            rvComics = itemView.findViewById(R.id.rvComics);
            ivArrow = itemView.findViewById(R.id.ivArrow);
        }

        // Update the view inside of the view holder with this data
        public void bind(String possStatus) {
            // Setting text for status
            tvStatus.setText(possStatus);
        }
    }
}
