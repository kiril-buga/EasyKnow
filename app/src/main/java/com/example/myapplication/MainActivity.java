package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import EasyKnowLib.LearnFolder;
import Notifications.NotificationManagerActivity;
import Notifications.NotificationReceiver;
import Notifications.NotificationsService;
import Notifications.Services;

import static com.example.myapplication.EasyKnow.CHANNEL_1_ID;


public class MainActivity extends AppCompatActivity {

    public DatabaseHelper myDB;
    private ArrayList<LearnFolder> foldersList;

    private NotificationManagerCompat notificationManager;
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

        //Assign variable
        btShow = findViewById(R.id.bt_show);

        // Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        myToolbar.setTitle("My Topics");
        setSupportActionBar(myToolbar);

        //Notifications
        notificationManager = NotificationManagerCompat.from(this);

        btShow.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {







                startService(v);
            }
        });

    }

    //Foreground service
    public void startService(View v) {
        String input = "This service is currently running";

        Intent serviceIntent = new Intent(this, Services.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, Services.class);
        stopService(serviceIntent);
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
            showMessage("Welcome!", "Please, add new topics and words");
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