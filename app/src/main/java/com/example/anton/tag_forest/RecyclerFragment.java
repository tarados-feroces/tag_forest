package com.example.anton.tag_forest;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class RecyclerFragment extends Fragment {

    public static RecyclerFragment newInstance() {
        RecyclerFragment myFragment = new RecyclerFragment();

        Bundle bundle = new Bundle();
        myFragment.setArguments(bundle);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TagAdapter tagAdapter = new TagAdapter(getContext(), this::onItemClick);

        RecyclerView tags = view.findViewById(R.id.tags_list);
        tags.setLayoutManager(new GridLayoutManager(getContext(), getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3));
        tags.setAdapter(tagAdapter);
        tags.setHasFixedSize(true);

        tagAdapter.add("Images");
        tagAdapter.add("Documents");
        tagAdapter.add("Video");
    }

    private void onItemClick(String i) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, TagFragment.newInstance(i))
                .addToBackStack(null)
                .commit();
    }

}