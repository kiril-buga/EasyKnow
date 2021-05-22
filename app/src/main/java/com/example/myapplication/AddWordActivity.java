package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWordActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    //private static final String FILE_NAME = "data.txt";
    private EditText editTextWord;
    private Button btnSaveWord;
    private Button btnSaveWordCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        editTextWord = findViewById(R.id.WordName);
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

        if(sWord.isEmpty()){
            editTextWord.setError("Word required");
            editTextWord.requestFocus();
            return;
        }
        else {
           /* boolean isInserted = myDB.insertNewWord(sWord);

            if (isInserted) {
                Toast.makeText(AddWordActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            }*/
        }
    }

    // switches back to the WordsView
    private void goToWordActivity() {
        Intent intent = new Intent(AddWordActivity.this, WordActivity.class );
        startActivity(intent);
    }

}