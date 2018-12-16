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


public class TagAdapter extends RecyclerView.Adapter<TagAdapter.NumbersRecyclerViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Integer> data;

    private final OnItemClickListener<Integer> onItemClickListener;


    public TagAdapter(Context context, OnItemClickListener<Integer> onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NumbersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumbersRecyclerViewHolder(layoutInflater.inflate(R.layout.tag, parent, false));
    }

    @Override
    public void onBindViewHolder(NumbersRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
        holder.number.setTextColor(position % 2 == 1 ? Color.RED : Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(Integer newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

    final static class NumbersRecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;


        NumbersRecyclerViewHolder(View tagView) {
            super(tagView);
            number = tagView.findViewById(R.id.num);
        }

        void bind(final Integer i, OnItemClickListener onItemClickListener) {
            number.setText(i.toString());

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));
        }


    }
}
