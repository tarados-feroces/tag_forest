package com.example.anton.tag_forest.TagDB.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TagFile {
    @NonNull
    @PrimaryKey
    public Integer tagId;
    public Integer fileId;

    @Override
    public String toString() {
        return tagId + "->" + fileId;
    }
}
