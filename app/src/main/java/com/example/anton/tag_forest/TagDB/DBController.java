package com.example.anton.tag_forest.TagDB;

import android.app.Application;
import android.arch.persistence.room.Room;

//import static com.example.anton.tag_forest.TagDB.TFDatabase.MIGRATION_1_2;

public class DBController extends Application {

    public static DBController instance;

    private TFDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, TFDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DBController getInstance() {
        return instance;
    }

    public TFDatabase getDatabase() {
        return database;
    }
}

