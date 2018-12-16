package com.example.anton.tag_forest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagsRecyclerViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<String> data;

    private final OnItemClickListener<String> onItemClickListener;


    public TagAdapter(Context context, OnItemClickListener<String> onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TagsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsRecyclerViewHolder(layoutInflater.inflate(R.layout.tag, parent, false));
    }

    @Override
    public void onBindViewHolder(TagsRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
        holder.tag.setTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(String name) {
        data.add(0, name);
        notifyItemInserted(0);
    }

    final static class TagsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView tag;


        TagsRecyclerViewHolder(View tagView) {
            super(tagView);
            tag = tagView.findViewById(R.id.container);
        }

        void bind(final String i, OnItemClickListener onItemClickListener) {
            tag.setText(i);

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));
        }
    }
}
