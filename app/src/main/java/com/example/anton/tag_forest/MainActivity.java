package com.example.anton.tag_forest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.anton.tag_forest.TagDB.DatabaseManager;
import com.example.anton.tag_forest.TagDB.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RecyclerFragment.newInstance())
                .commit();

        DatabaseManager db = DatabaseManager.getInstance(this);

        db.addTag(Tag.toTag("Video"));
        db.addTag(Tag.toTag("Music"));
        db.addTag(Tag.toTag("Data"));
        db.addTag(Tag.toTag("Math"));
        db.addTag(Tag.toTag("Sport"));
        db.addTag(Tag.toTag("Photo"));
        db.addTag(Tag.toTag("Algebra"));
        db.addTag(Tag.toTag("F9"));

        db.getAllTags(readListener);
    }

    public void goToSearch(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

    private void showStringList(final Collection<Tag> tags) {
        final List<String> list = new ArrayList<>();
        for (Tag tag: tags) {
            list.add(tag.getName());
        }
        new AlertDialog.Builder(this)
                .setItems(list.toArray(new String[0]), null)
                .show();
    }

    private final DatabaseManager.ReadTagsListener<Tag> readListener = new DatabaseManager.ReadTagsListener<Tag>() {

        @Override
        public void onGetTags(final Collection<Tag> tags) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showStringList(tags);
                }
            });
        }
    };

}
