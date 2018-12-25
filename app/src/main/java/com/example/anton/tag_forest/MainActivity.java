package com.example.anton.tag_forest;

import android.support.v7.app.AlertDialog;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Pair;

import com.example.anton.tag_forest.TagDB.DatabaseManager;
import com.example.anton.tag_forest.TagDB.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.anton.tag_forest.filemanager.*;


public class MainActivity extends AppCompatActivity {

    FileManagerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED || readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
        }


        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        fragment = new FileManagerFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tag_container, RecyclerFragment.newInstance())
                .replace(R.id.container, fragment)
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

//    public void goToSearch(View view) {
//        Intent searchIntent = new Intent(this, SearchActivity.class);
//        startActivity(searchIntent);
//    }

    private void showStringList(final Collection<Tag> tags) {
        final List<String> list = new ArrayList<>();
        for (Tag tag : tags) {
            list.add(tag.getName());
        }
        new AlertDialog.Builder(this)
                .setItems(list.toArray(new String[0]), null)
                .show();
    }

    private final DatabaseManager.ReadTagsListener<Tag> readListener = tags -> runOnUiThread(() -> showStringList(tags));

    @Override
    public void onBackPressed() {
        if (!fragment.getAdapter().goBack()) {
            super.onBackPressed();
        }
    }

}
