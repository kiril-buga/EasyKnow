package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddFolderActivity extends AppCompatActivity {

    private DatabaseHelper myDB;
    private static final String FILE_NAME = "data.txt";
    private EditText editTextFolder;
    private Button btnSaveFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        editTextFolder = findViewById(R.id.FolderName);
        btnSaveFolder = findViewById(R.id.btSave);

        myDB = new DatabaseHelper(this);

        btnSaveFolder.setOnClickListener((view)->{
            // save to a file
            saveFolder();


            // switch back to main menu
            Intent intent = new Intent(AddFolderActivity.this, MainActivity.class );
            startActivity(intent);
        });
    }

    public void saveFolder(){
        final String sFolder = editTextFolder.getText().toString().trim();

        if(sFolder.isEmpty()){
            editTextFolder.setError("Folder required");
            editTextFolder.requestFocus();
            return;
        }
        else {
            boolean isInserted = myDB.insertData(sFolder);

            if (isInserted) {
                Toast.makeText(AddFolderActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
            }
        }
//        FileOutputStream fos = null;
//
//        try {
//            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            fos.write(sFolder.getBytes());
//
//            editTextFolder.getText().clear();
//            Toast.makeText(this, "Saved to " + getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }





        // ADAPT SO THAT IT SAVES ON THE PHONE


//        class SaveFolder extends AsyncTask<Void, Void, Void> {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                LearnFolder task = new LearnFolder();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                finish();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
//            }
//        }
    }

    public void loadFolder(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }
            // load to

            editTextFolder.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}