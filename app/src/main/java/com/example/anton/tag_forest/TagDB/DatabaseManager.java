package com.example.anton.tag_forest.TagDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.anton.tag_forest.TagDB.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DatabaseManager {

    @SuppressLint("StaticFieldLeak")
    private static final DatabaseManager INSTANCE = new DatabaseManager();

    private static final String DB_NAME = "TagFileDatabase.db";
    private static final int VERSION = 1;

    public static DatabaseManager getInstance(Context context) {
        INSTANCE.context = context.getApplicationContext();
        return INSTANCE;
    }

    private Context context;

    private SQLiteDatabase database;

    private void createDatabase(SQLiteDatabase database) {
        database.execSQL(
                "CREATE TABLE tags (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);"
        );
    }

    public void addTag(Tag tag) {
        checkInitialized();

        ContentValues values = new ContentValues();
        values.put("name", tag.getName());
        database.insert("tags", null, values);
    }

    public void getAllTags(final ReadTagsListener<Tag> listener) {
        checkInitialized();

        Cursor cursor = database.rawQuery("select id, name from tags;", null);
        if (cursor == null) {
            listener.onGetTags(Collections.emptyList());
            return;
        }

        final List<Tag> result = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                result.add(Tag.toTag(cursor.getString(cursor.getColumnIndex("name"))));
            }
        } finally {
            cursor.close();
        }
        listener.onGetTags(result);
    }


    private void checkInitialized() {
        if (database != null) {
            return;
        }

        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

            @Override
            public void onCreate(SQLiteDatabase db) {
                createDatabase(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS tags");
                onCreate(db);
            }
        };

        database = helper.getWritableDatabase();
    }


    public interface ReadTagsListener<T> {
        void onGetTags(final Collection<T> allTags);
    }
}