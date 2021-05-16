package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        TextView nameTxt = findViewById(R.id.nameTextView);

        String foldername = "Folder name not set";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            foldername = extras.getString("folder name");
        }

        nameTxt.setText(foldername);
    }
}