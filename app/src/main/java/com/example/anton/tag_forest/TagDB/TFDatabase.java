package com.example.anton.tag_forest.TagDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

@Database(entities = {Tag.class, Files.class, TagFile.class}, version = 3, exportSchema = false)
public abstract class TFDatabase extends RoomDatabase {
    public abstract TFDao tfDao();

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
}

