package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import EasyKnowLib.LearnItem;

public class WordActivity extends AppCompatActivity {
    private ArrayList<LearnItem> wordsList;
    private RecyclerView recyclerView;
    private WordsAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        recyclerView = findViewById(R.id.wordsRecyclerView);
        wordsList = new ArrayList<>();

        setWordInfo();
        setAdapter();
    }

    private void setAdapter() {
        setOnClickListener();
        WordsAdapter adapter = new WordsAdapter(wordsList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new WordsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        };
    }

    private void setWordInfo() {
        wordsList.add(new LearnItem("Hallo", "hello"));
        wordsList.add((new LearnItem("Kacke", "shit")));
        wordsList.add((new LearnItem("Alter Mann", "Old man")));
    }
}