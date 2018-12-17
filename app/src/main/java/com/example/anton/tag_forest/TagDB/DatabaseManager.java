package com.example.anton.tag_forest.TagDB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseManager {

    @SuppressLint("StaticFieldLeak")
    private static final DatabaseManager INSTANCE = new DatabaseManager();

    static DatabaseManager getInstance(Context context) {
        INSTANCE.context = context.getApplicationContext();
        return INSTANCE;
    }

    private final ExecutorService executor =  Executors.newSingleThreadExecutor();

    private Context context;

    private  TFDatabase db;
    private TFDao tfDao;

    public DatabaseManager() {
        db = DBController.getInstance().getDatabase();
        tfDao = db.tfDao();
    }

    //TODO: new adding to all tables
    //TODO: rewrite with callable if you want
    public void addFileByTag(String file, String tagName) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Tag newTag = new Tag();
                newTag.name = tagName;
                tfDao.addTag(newTag);

                Files newFile = new Files();
                newFile.name = file;
                tfDao.addFile(newFile);
            }
        });

    }

    //TODO: make recieving with JOIN
    public String getAllFiles() {
        List<Tag> tags = new ArrayList<Tag>();
        List<Files> files = new ArrayList<Files>();

        Future<List<Tag>> future_t;
        Future<List<Files>> future_f;

        future_t = executor.submit(new Callable<List<Tag>>() {
            @Override
            public List<Tag> call() {
                return tfDao.getAllTags();
            }
        });

        try {
            tags = future_t.get();
        } catch (Exception e) {
            // failed
        }

        future_f = executor.submit(new Callable<List<Files>>() {
            @Override
            public List<Files> call() {
                return tfDao.getAllFiles();
            }
        });

        try {
            files = future_f.get();
        } catch (Exception e) {
            // failed
        }

        String result = "";
        for (int i = 0; i < tags.size(); ++i) {
            result += tags.get(i) + " : " + files.get(i) + "\n";
        }
        return result;
    }
}
