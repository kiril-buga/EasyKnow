package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import EasyKnowLib.LearnItem;

public class WordActivity extends AppCompatActivity {
    private ArrayList<LearnItem> wordList;
    private RecyclerView recyclerView;
    private WordsAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        recyclerView = findViewById(R.id.wordsRecyclerView);
        wordList = new ArrayList<>();

        setWordInfo();
        setAdapter();
    }

    private void setAdapter() {
        WordsAdapter adapter = new WordsAdapter(wordList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setWordInfo() {
        wordList.add(new LearnItem("Hallo", "hello"));
        wordList.add((new LearnItem("Kacke", "shit")));
        wordList.add((new LearnItem("Alter Mann", "Old man")));
    }
}