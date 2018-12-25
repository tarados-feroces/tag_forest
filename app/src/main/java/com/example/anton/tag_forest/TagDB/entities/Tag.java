package com.example.anton.tag_forest.TagDB.entities;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Tag {

    @NonNull
    private String name;

    @Nullable
    private String color;

    @Nullable
    private Long id;

    public Tag(@NonNull String name) {
        this.name = name;
    }

    public Tag(@Nullable Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(@NonNull String name, @Nullable String color) {
        this.name = name;
        this.color = color;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getColor() {
        return color;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public static Tag toTag(@NonNull String name) {
        return new Tag(name);
    }
}


