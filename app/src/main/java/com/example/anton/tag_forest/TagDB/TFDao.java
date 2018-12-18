package com.example.anton.tag_forest.TagDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.anton.tag_forest.TagDB.entities.Files;
import com.example.anton.tag_forest.TagDB.entities.Tag;

import java.util.List;



@Dao
public interface TFDao {

    @Insert
    void addTag(Tag tag);

    @Insert
    void addFile(Files file);





    @Query("SELECT * FROM tag")
    List<Tag> getAllTags();

    @Query("SELECT * FROM files")
    List<Files> getAllFiles();

//    @Query("SELECT * FROM files")
//    void lol();

}