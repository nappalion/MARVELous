package com.example.marvelous.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marvelous.R;
import com.example.marvelous.models.Comic;
import com.parse.ParseFile;

import java.util.List;
/*
public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private Context context;
    private List<Comic> comics;

    public ComicsAdapter(Context context, List<Comic> posts) {
        this.context = context;
        this.comics = comics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comic comic = comics.get(position);
        holder.bind(comic);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView tvComicname;
        private TextView tvDescription;
        private ImageView ivImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvComicname = itemView.findViewById(R.id.tvComicname);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

        public void bind(Comic comic) {
            tvDescription.setText(comic.getDescription());
//            tvComicname.setText(comic.getComic().getComicname());
            ParseFile image = comic.getImage();
            if(image != null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

        }
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
}
*/