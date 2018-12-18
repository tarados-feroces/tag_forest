package com.example.anton.tag_forest.TagDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.anton.tag_forest.TagDB.entities.Files;
import com.example.anton.tag_forest.TagDB.entities.Tag;
import com.example.anton.tag_forest.TagDB.entities.TagFile;

@Database(entities = {Tag.class, Files.class, TagFile.class}, version = 3, exportSchema = false)
public abstract class TFDatabase extends RoomDatabase {
    public abstract TFDao tfDao();
}

