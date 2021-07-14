package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddFolderActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private EditText editTextFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        editTextFolder = findViewById(R.id.WordName);
        Button btnSaveFolder = findViewById(R.id.btSaveWord);
        Button btnSaveFolderCancel = findViewById(R.id.btCancel);

        myDB = new DatabaseHelper(this);

        btnSaveFolder.setOnClickListener((view)->{
            // save to a file
            saveFolder();
        });

        btnSaveFolderCancel.setOnClickListener((view)-> goToMainActivity());
    }

    public void saveFolder(){
        final String sFolder = editTextFolder.getText().toString().trim();

        if(sFolder.isEmpty()){
            editTextFolder.setError("Topic required");
            editTextFolder.requestFocus();
        }
        else {
            boolean isInserted = myDB.insertNewFolder(sFolder);

            if (isInserted) {
                Toast.makeText(AddFolderActivity.this, "New topic inserted", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            } else{
                editTextFolder.setError("This topic already exists");
                editTextFolder.requestFocus();
            }

        }

    }

    private void goToMainActivity(){
        Intent intent = new Intent(AddFolderActivity.this, MainActivity.class );
        startActivity(intent);
    }

}