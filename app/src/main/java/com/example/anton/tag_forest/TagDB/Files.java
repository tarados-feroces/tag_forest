package com.example.anton.tag_forest.TagDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Files {
    @NonNull
    @PrimaryKey
    public Integer id;
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
