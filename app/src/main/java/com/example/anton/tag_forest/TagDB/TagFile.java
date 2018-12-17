package com.example.anton.tag_forest.TagDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TagFile {
    @NonNull
    @PrimaryKey
    public Integer tag_id;
    public Integer file_id;

    @Override
    public String toString() {
        return tag_id + " : " + file_id;
    }
}
