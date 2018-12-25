package com.example.anton.tag_forest.TagDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.anton.tag_forest.TagDB.entities.File;
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
        database.execSQL(
                "CREATE TABLE files (id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT NOT NULL UNIQUE);"
        );
        database.execSQL(
                "CREATE TABLE tag_files (id INTEGER PRIMARY KEY AUTOINCREMENT, tag_id INTEGER references tags(id), file_id INTEGER references files(id));"
        );
        database.execSQL(
                "CREATE UNIQUE INDEX IF NOT EXISTS tag_files_index ON tag_files(tag_id, file_id);"
        );
        database.execSQL(
                "CREATE UNIQUE INDEX IF NOT EXISTS files_tag_index ON tag_files(file_id, tag_id);"
        );
    }



    @Nullable
    public Tag getTagByName(@NonNull String tagName) {
        checkInitialized();

        Cursor cursor = database.rawQuery("select * from tags where lower(name) == lower(?) limit 1", new String[]{tagName});

        if (cursor == null) {
            return null;
        }

        try {
            if (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                cursor.close();
                return new Tag(id, name);
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    @Nullable
    public File getFileByName(@NonNull String fileName) {
        checkInitialized();

        Cursor cursor = database.rawQuery("select id, name from files where name == ? limit 1", new String[]{fileName});

        if (cursor == null) {
            return null;
        }

        try {
            if (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                cursor.close();
                return new File(id, name);
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    public Long addTag(Tag tag) {
        checkInitialized();

        ContentValues values = new ContentValues();
        values.put("name", tag.getName());
        final long tagId = database.insertWithOnConflict("tags", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (tagId == -1) {
            return null;
        }
        return tagId;
    }

    public Long addFile(File file) {
        checkInitialized();

        ContentValues values = new ContentValues();
        values.put("name", file.getName());
        final long fileId = database.insertWithOnConflict("files", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (fileId == -1) {
            return null;
        }
        return fileId;
    }

    private String addTagFile(Tag tag, File file) {
        checkInitialized();

        Tag selectedTag = getTagByName(tag.getName());
        if (selectedTag == null) {
            Long tagId = addTag(tag);
            if (tagId == null) {
                return "Error: can't insert tag(" + tag.getName() + ") into table tags";
            }
            selectedTag = new Tag(tagId, tag.getName());
        }

        File selectedFile = getFileByName(tag.getName());
        if (selectedFile == null) {
            Long fileId = addFile(file);
            if (fileId == null) {
                return "Error: can't insert file(" + file.getName() + ") into table files";
            }
            selectedFile = new File(fileId, file.getName());
        }

        ContentValues values = new ContentValues();
        values.put("file_id", selectedFile.getId());
        values.put("tag_id", selectedTag.getId());
        final long fileId = database.insertWithOnConflict("tag_files", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (fileId == -1) {
            return "Error: can't insert ids pair into table tag_files";
        }
        return "All Done!";
    }


    public void addTagAndFile(String tagName, String fileName, TagFileAdditionListener listener) {
        String response = addTagFile(new Tag(tagName), new File(fileName));
        listener.onAdditionTagFile(response);
    }


    public void selectTags(final TagSelectionListener<Tag> listener, final int count) {
        checkInitialized();

        Cursor cursor = database.rawQuery("select id, name from tags limit " + Integer.toString(count), null);
        if (cursor == null) {
            listener.onGetTags(Collections.emptyList());
            return;
        }

        final List<Tag> result = new ArrayList<>();
        
        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                result.add(new Tag(id, name));
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


    public interface TagFileAdditionListener {
        void onAdditionTagFile(final String response);
    }

    public interface TagSelectionListener<T> {
        void onGetTags(final Collection<T> tags);
    }

    public interface FileSelectionListener<T> {
        void onGetFiles(final Collection<T> files);
    }
}