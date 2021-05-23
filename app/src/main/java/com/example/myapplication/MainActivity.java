package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import EasyKnowLib.LearnFolder;


public class MainActivity extends AppCompatActivity {

    public DatabaseHelper myDB;
    private ArrayList<LearnFolder> foldersList;

    private Button btShow;
    private FloatingActionButton btAdd;
    private RecyclerView recyclerView;
    private FoldersAdapter.RecyclerViewClickListener listener;

    private AddFolderActivity addFolderActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database
        myDB = new DatabaseHelper(this);


        //Folders
        recyclerView = findViewById(R.id.recyclerView);

        foldersList = new ArrayList<>();

        setFolderInfo();
        setAdapter();

        //Toolbar mainToolbar = (Toolbar) findViewById(R.id.MainToolbar);
        //setSupportActionBar(mainToolbar);
        //btAdd

        btAdd = findViewById(R.id.btAddWord);
        btAdd.setOnClickListener((view)->{
            Intent intent = new Intent(MainActivity.this, AddFolderActivity.class);
            startActivity(intent);
            Toast.makeText(this, "The button was clicked " , Toast.LENGTH_LONG).show();
        });

        //Notifications
        createNotificationChannel();

        //Assign variable
        btShow = findViewById(R.id.bt_show);

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(myToolbar);

        //Convert image type to bitmap
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Notification")
                .setContentText("This is notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(icon);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        btShow.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                notificationManager.notify(100, builder.build());
            }
        });

    }

    private void setAdapter() {
        setOnClickListener();
        FoldersAdapter adapter = new FoldersAdapter(foldersList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new FoldersAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("folderTitle", foldersList.get(position).getFolderTitle());

                //Toast.makeText(MainActivity.this, folderTitle,Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        };
    }

    private void setFolderInfo() {
        //addFolderActivity.loadFolder();

        Cursor res = myDB.getAllFolders();
        if(res.getCount()==0){
            // show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append(res.getString(1) + "\n");

        }

        // show all data
        String[] folders = buffer.toString().split("\n");
        for(int i = 0; i<folders.length; i++){
            foldersList.add(new LearnFolder(folders[i]));
        }

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                return true;
            case R.id.item_notifications:
                Intent intent = new Intent(MainActivity.this, NotificationManagerActivity.class );
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}