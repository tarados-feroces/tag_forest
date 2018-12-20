package com.example.anton.tag_forest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            TextView textView = null;

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchView", "onQueryTextSubmit: start");
                if (textView == null) {
                    textView = findViewById(R.id.files_view_container);
                }
                Log.d("searchView", "onQueryTextSubmit: preText");

                textView.setText("Search: " + query);

                Log.d("searchView", "onQueryTextSubmit: end");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchView", "onQueryTextChange: start");
                if (textView == null) {
                    textView = findViewById(R.id.files_view_container);
                }
                Log.d("searchView", "onQueryTextChange: preText");

                textView.setText("Change: " + searchView.getQuery());

                Log.d("searchView", "onQueryTextChange: end");
                return false;
            }
        });
    }
}
