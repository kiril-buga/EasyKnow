package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import EasyKnowLib.LearnFolder;
import EasyKnowLib.LearnItem;

public class WordActivity extends AppCompatActivity {
    public DatabaseHelper myDB;
    private ArrayList<LearnItem> wordsList;

    private RecyclerView recyclerView;
    private WordsAdapter.RecyclerViewClickListener listener;
    private FloatingActionButton btAdd;

    private String folderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        //DB
        myDB = new DatabaseHelper(this);

        //Get all extras from MainActivity
        if (getIntent().hasExtra("folderTitle")) {
            folderTitle = getIntent().getStringExtra("folderTitle");
        }

        //Words
        wordsList = new ArrayList<>();
        recyclerView = findViewById(R.id.wordsRecyclerView);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.wordActivityToolbar);
        myToolbar.setTitle("My Words");
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setWordInfo();
        setAdapter();

        //Controls
        btAdd = findViewById(R.id.btAddWord);
        btAdd.setOnClickListener((view)->{
            Intent intent = new Intent(WordActivity.this, AddWordActivity.class);
            if (!folderTitle.isEmpty()) {
                intent.putExtra("folderTitle", folderTitle);
                Toast.makeText(WordActivity.this, "FolderTitle:"+folderTitle, Toast.LENGTH_LONG).show();
            }
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
        //Get folder ID
        Cursor res = myDB.getFolderId(folderTitle);
        res.moveToFirst();
        String folderId = res.getString(0);
        //Get all words
        res = myDB.getAllWords();

        if(res.getCount()==0){
            return;
        }
        StringBuffer bufferWords = new StringBuffer();
        StringBuffer bufferMeanings = new StringBuffer();
        while(res.moveToNext()) {
            if(res.getString(4).equals(folderId)) {
                bufferWords.append(res.getString(1) + "\n");
                bufferMeanings.append(res.getString(2) + "\n");
            }
        }

        // show all data
        String[] words = bufferWords.toString().split("\n");
        String[] meanings = bufferMeanings.toString().split("\n");
        for(int i = 0; i<words.length; i++){
            wordsList.add(new LearnItem(words[i],meanings[i]));
        }
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