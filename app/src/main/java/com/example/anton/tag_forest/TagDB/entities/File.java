package com.example.anton.tag_forest.TagDB.entities;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class File {

    @NonNull
    final private String name;
    private List<Tag> tagList;

    @Nullable
    private Long id;

    public File(@NonNull String name) {
        this.name = name;
        this.tagList = new ArrayList<>();
    }

    public File(@Nullable Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
        this.tagList = new ArrayList<>();
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<Tag> getTagList() {
        return tagList;
    }
}
