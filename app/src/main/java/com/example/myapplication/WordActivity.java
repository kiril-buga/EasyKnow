package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import EasyKnowLib.LearnItem;

public class WordActivity extends AppCompatActivity {
    public DatabaseHelper myDB;
    private ArrayList<LearnItem> wordsList;

    private RecyclerView recyclerView;
    private WordsAdapter.RecyclerViewClickListener listener;
    private FloatingActionButton btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        // DB
        myDB = new DatabaseHelper(this);

        // Words
        wordsList = new ArrayList<>();
        recyclerView = findViewById(R.id.wordsRecyclerView);

        setWordInfo();
        setAdapter();

        // Controls
        btAdd = findViewById(R.id.btAddWord);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btAdd.setOnClickListener((view)->{
            Intent intent = new Intent(WordActivity.this, AddWordActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Create new word", Toast.LENGTH_LONG).show();
        });
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(WordActivity.this, MainActivity.class );
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}