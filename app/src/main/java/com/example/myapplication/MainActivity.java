package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private Button btShow;
    private FloatingActionButton btAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar mainToolbar = (Toolbar) findViewById(R.id.MainToolbar);
        //setSupportActionBar(mainToolbar);
        //Recycler View and btAdd
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener((view)->{
            Intent intent = new Intent(MainActivity.this, AddFolderActivity.class);
            startActivity(intent);
            Toast.makeText(this, "The button was clicked " , Toast.LENGTH_LONG).show();
        });


        //Notifications
        createNotificationChannel();

        //Assign variable
        btShow = findViewById(R.id.bt_show);

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




}