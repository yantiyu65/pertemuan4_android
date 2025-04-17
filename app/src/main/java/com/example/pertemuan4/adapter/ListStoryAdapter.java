package com.example.pertemuan4.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pertemuan4.R;
import com.example.pertemuan4.model.Story;
import com.example.pertemuan4.ui.DetailCeritaActivity;
import com.example.pertemuan4.util.Constans;

import java.util.List;

public class ListStoryAdapter extends RecyclerView.Adapter<ListStoryAdapter.ViewHolder> {
    private List<Story> storyList;
    private Context context;

    public ListStoryAdapter(List<Story> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.storyTitle.setText(story.getTitle());

        // Load gambar menggunakan Glide
        Glide.with(context)
                .load(story.getImage())
                .placeholder(R.drawable.no1) // Placeholder jika gambar gagal dimuat
                .into(holder.storyCover);

        // Klik pada CardView (layClick) untuk membuka DetailCeritaActivity
        holder.layClick.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCeritaActivity.class);
            intent.putExtra(Constans.EXTRA_TITLE, story.getTitle());
            intent.putExtra(Constans.EXTRA_STORY_TEXT, story.getStoryText());
            intent.putExtra(Constans.EXTRA_AUDIO, story.getAudio());
            intent.putExtra(Constans.EXTRA_IMAGE, story.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storyTitle;
        ImageView storyCover;
        CardView layClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layClick = itemView.findViewById(R.id.layClick);
            storyTitle = itemView.findViewById(R.id.story_title);
            storyCover = itemView.findViewById(R.id.story_cover);
        }
    }

}
