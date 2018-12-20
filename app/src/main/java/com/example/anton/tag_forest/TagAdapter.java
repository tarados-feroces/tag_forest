package com.example.anton.tag_forest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagsRecyclerViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<String> data;


    TagAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagsRecyclerViewHolder(layoutInflater.inflate(R.layout.tag_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.tag.setTextColor(Color.WHITE);
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
            tag = tagView.findViewById(R.id.tag_name);
        }

        void bind(final String name) {
            tag.setText(name);

            itemView.setOnClickListener(v -> Toast.makeText(tag.getContext(), name, Toast.LENGTH_LONG).show());
        }
    }
}
