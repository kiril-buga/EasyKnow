package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class AddFolderActivity extends AppCompatActivity {

    private EditText editTextFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        editTextFolder = findViewById(R.id.FolderName);

        findViewById(R.id.btSave).setOnClickListener((view)->{
            saveFolder();
        });
    }

    private void saveFolder(){
        final String sFolder = editTextFolder.getText().toString().trim();

        if(sFolder.isEmpty()){
            editTextFolder.setError("Folder required");
            editTextFolder.requestFocus();
            return;
        }

        // ADAPT SO THAT IT SAVES ON THE PHONE
        class SaveFolder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Folder task = new Folder();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}