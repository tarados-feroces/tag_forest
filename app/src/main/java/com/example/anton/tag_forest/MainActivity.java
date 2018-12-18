package com.example.anton.tag_forest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.anton.tag_forest.TagDB.DatabaseManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RecyclerFragment.newInstance())
                .commit();

        DatabaseManager db = DatabaseManager.getInstance(this);
//        db.addFileByTag("/home/danchetto/12345", "LOL");
        Log.d("wat?", db.getAllFiles().toString());
    }

    public void goToSearch(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

}
