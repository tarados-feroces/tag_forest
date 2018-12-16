package com.example.anton.tag_forest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TagFragment  extends Fragment {

    private final static String KEY = "lol";
    private String tag_name;
    TextView textView;

    public static TagFragment newInstance(String data) {
        TagFragment myFragment = new TagFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY, data);

        myFragment.setArguments(bundle);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            tag_name = arguments.getString(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tag_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.tag);

        textView.setTextColor(getResources().getColor(R.color.cyan));


        Log.d("AAAAAAAAAA", "AAAAAAAAAA");

        textView.setText(tag_name);
    }
}