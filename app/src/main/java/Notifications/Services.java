package Notifications;

import java.util.*;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.renderscript.RenderScript;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.*;

import EasyKnowLib.Day;
import EasyKnowLib.Week;

import static com.example.myapplication.EasyKnow.CHANNEL_0_ID;

public class Services extends Service {

    private DatabaseHelper myDB;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        String input = intent.getStringExtra("inputExtra");

        //Database
        myDB = new DatabaseHelper(this);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        //Convert image type to bitmap
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_0_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification service")
                .setContentText(input)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setLargeIcon(icon)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
        setAlarm();

        return START_NOT_STICKY; //START_REDELIVER_INTENT
    }

    private void setAlarm() {



        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Toast.makeText(getApplicationContext(), calendar.getTime().toString(),Toast.LENGTH_LONG).show();
        calendar.add(Calendar.SECOND, 2);
        Toast.makeText(getApplicationContext(), calendar.getTime().toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), NotificationsService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                getApplicationContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager AlarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        AlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1000 * 60 * 60 * 24, pendingIntent);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
