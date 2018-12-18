package com.example.anton.tag_forest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.anton.tag_forest.TagDB.DatabaseManager;
import com.example.anton.tag_forest.filemanager.FileManagerFragment;

import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED || readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
        }

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                //.replace(R.id.container, RecyclerFragment.newInstance())
                .replace(R.id.container, new FileManagerFragment())
                .commit();

        findViewById(R.id.btn_file_manager).setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, SearchActivity.class)
        ));

        DatabaseManager db = new DatabaseManager();
    }

    public void goToSearch(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

}
