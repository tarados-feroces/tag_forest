package com.example.anton.tag_forest.TagDB.entities;


import android.support.annotation.NonNull;

public class Tag {

    @NonNull
    private String name;

    public Tag(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public static Tag toTag(@NonNull String name) {
        return new Tag(name);
    }
}


