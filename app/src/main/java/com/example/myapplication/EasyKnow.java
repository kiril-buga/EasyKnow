package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class EasyKnow extends Application {
    public static final String CHANNEL_1_ID = "Check answer";
    //public static final String CHANNEL_2_ID;

    @Override
    public void onCreate() {
        super.onCreate();
        
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Check answer",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This channel checks your answers");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }
}
