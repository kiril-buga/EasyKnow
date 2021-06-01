package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import EasyKnowLib.LearnFolder;

public class AddWordActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    //private static final String FILE_NAME = "data.txt";
    private EditText editTextWord;
    private EditText editTextMeaning;
    private Button btnSaveWord;
    private Button btnSaveWordCancel;

    private String folderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        editTextWord = findViewById(R.id.WordName);
        editTextMeaning = findViewById(R.id.WordMeaning);
        btnSaveWord = findViewById(R.id.btSaveWord);
        btnSaveWordCancel = findViewById(R.id.btSaveWordCancel);

        myDB = new DatabaseHelper(this);

        btnSaveWord.setOnClickListener((view)->{
            // save to a file
            saveWord();
        });

        btnSaveWordCancel.setOnClickListener((view)->{
            goToWordActivity();
        });
    }

    public void saveWord(){
        final String sWord = editTextWord.getText().toString().trim();
        final String sMeaning = editTextMeaning.getText().toString().trim();
        final int learn_status = 0;
        final String sFolder_id;


        if(sWord.isEmpty() || sMeaning.isEmpty()){
            editTextWord.setError("Word and Meaning are required");
            editTextWord.requestFocus();
            return;
        }
        else {

            // GetData from recycler view
            if (getIntent().hasExtra("folderTitle")) {
                folderTitle = getIntent().getStringExtra("folderTitle");
                Cursor res = myDB.getFolderId(folderTitle);
                res.moveToFirst();
                sFolder_id = res.getString(0);
                if(res.getCount()==0) return;

                //Show data
                Toast.makeText(AddWordActivity.this, "FolderID: " + sFolder_id, Toast.LENGTH_LONG).show();

                boolean isInserted = myDB.insertNewWord(sWord, sMeaning, learn_status, sFolder_id, "");

                if (isInserted) {
                    Toast.makeText(AddWordActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                    goToWordActivity();
                }
            }


        }
    }

    // switches back to the WordsView
    private void goToWordActivity() {
        Intent intent = new Intent(AddWordActivity.this, WordActivity.class );
        intent.putExtra("folderTitle", folderTitle);
        startActivity(intent);
    }

}