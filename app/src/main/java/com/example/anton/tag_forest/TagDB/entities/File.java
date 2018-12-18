package com.example.anton.tag_forest.TagDB.entities;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class File {

    @NonNull
    final private String name;
    private List<Tag> tagList;

    public File(@NonNull String name) {
        this.name = name;
        this.tagList = new ArrayList<>();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<Tag> getTagList() {
        return tagList;
    }
}
