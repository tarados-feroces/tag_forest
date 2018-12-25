package com.example.anton.tag_forest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anton.tag_forest.TagDB.DatabaseManager;
import com.example.anton.tag_forest.TagDB.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

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

    private List<String> getPopularTags(final Collection<Tag> tagList) {
        final List<String> list = new ArrayList<>();
        for (Tag tag : tagList) {
            list.add(tag.getName());
        }
        return list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TagAdapter tagAdapter = new TagAdapter(getContext());

        RecyclerView tags = view.findViewById(R.id.tags_list);
        tags.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tags.setAdapter(tagAdapter);
        tags.setHasFixedSize(true);

        DatabaseManager db = DatabaseManager.getInstance(getContext());

        final DatabaseManager.ReadTagsListener<Tag> popularTagsReader =
                tagList -> new Handler(Looper.getMainLooper()).post(() -> getPopularTags(tagList));

        db.getPopularTags(popularTagsReader);

        //TODO: get values from DB
        tagAdapter.add("Images");
        tagAdapter.add("Documents");
        tagAdapter.add("Video");
        tagAdapter.add("Math");
        tagAdapter.add("Programming");
        tagAdapter.add("Books");
        tagAdapter.add("my_stuff");
        tagAdapter.add("Study");
        tagAdapter.add("Physics");
    }
}