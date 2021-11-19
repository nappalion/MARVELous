package com.example.marvelous.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvelous.R;
import com.example.marvelous.models.UserComic;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    Context context;
    public static final String TAG = "LibraryAdapter";

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    private List<String> mPossStatuses;
    OnClickListener clickListener;

    public LibraryAdapter(List<String> possStatus, OnClickListener clickListener) {
        this.mPossStatuses = possStatus;
        this.clickListener = clickListener;
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

        // LibraryComics RV Setup
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvComics.setLayoutManager(layoutManager);
        holder.rvComics.setHasFixedSize(true);

        List<UserComic> userComics = new ArrayList<>();

        // Query for all the UserComic for the current user
        ParseQuery<UserComic> query = ParseQuery.getQuery(UserComic.class);
        // Define the query conditions
        query.whereEqualTo("userId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<UserComic>() {
            @Override
            public void done(List<UserComic> comics, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for (UserComic comic: comics) {
                        if (comic.getStatus().equals(possStatus)) {
                            Log.i(TAG, comic.getStatus() + " - is equal to - " + possStatus);
                            // adds specific comic in specific row
                            userComics.add(comic);
                        }
                    }
                } else {
                    Log.d(TAG, "Issue with getting comics.");
                }
            }
        });

        // set item views based on views in data model
        holder.bind(possStatus);

        // Attach LibraryComicsAdapter to RV
        LibraryComicsAdapter libraryComicsAdapter = new LibraryComicsAdapter(userComics, holder.rvComics.getContext());
        holder.rvComics.setAdapter(libraryComicsAdapter);
    }

    @Override
    public int getItemCount() {
        return mPossStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStatus;
        public RecyclerView rvComics;
        public ImageView ivArrow;
        public LinearLayout libContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            rvComics = itemView.findViewById(R.id.rvComics);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            libContainer = itemView.findViewById(R.id.libContainer);
        }

        // Update the view inside of the view holder with this data
        public void bind(String possStatus) {
            // Setting text for status
            tvStatus.setText(possStatus);

            // Added OnClickListener for each tvStatus
            libContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
