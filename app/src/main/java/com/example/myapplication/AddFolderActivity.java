package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddFolderActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private static final String FILE_NAME = "data.txt";
    private EditText editTextFolder;
    private Button btnSaveFolder;
    private Button btnSaveFolderCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        editTextFolder = findViewById(R.id.WordName);
        btnSaveFolder = findViewById(R.id.btSaveWord);
        btnSaveFolderCancel = findViewById(R.id.btCancel);

        myDB = new DatabaseHelper(this);

        btnSaveFolder.setOnClickListener((view)->{
            // save to a file
            saveFolder();
        });

        btnSaveFolderCancel.setOnClickListener((view)->{
            goToMainActivity();
        });
    }

    public void saveFolder(){
        final String sFolder = editTextFolder.getText().toString().trim();

        if(sFolder.isEmpty()){
            editTextFolder.setError("Folder required");
            editTextFolder.requestFocus();
        }
        else {
            boolean isInserted = myDB.insertNewFolder(sFolder);

            if (isInserted) {
                Toast.makeText(AddFolderActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            }
            goToMainActivity();
        }

    }

//    public void loadFolder(){
//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
//
//            while((text = br.readLine()) != null){
//                sb.append(text).append("\n");
//            }
//            // load to
//
//            editTextFolder.setText(sb.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void goToMainActivity(){
        Intent intent = new Intent(AddFolderActivity.this, MainActivity.class );
        startActivity(intent);
    }

}