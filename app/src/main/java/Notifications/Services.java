package Notifications;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.renderscript.RenderScript;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.*;

import EasyKnowLib.Day;
import EasyKnowLib.NotificationSettings;
import EasyKnowLib.Week;

import static com.example.myapplication.EasyKnow.CHANNEL_0_ID;

public class Services extends Service {

    private DatabaseHelper myDB;
    static int currentNotificationNumber = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        String input = intent.getStringExtra("inputExtra");
        //notificationDestroyed = false;

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

        alarmRunnable runnable = new alarmRunnable();
        new Thread(runnable).start();


//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1000 * 60 * 60 * 24, pendingIntent);

    }

    class alarmRunnable implements Runnable {


        @Override
        public void run() {
            NotificationSettings notificationSettings = getNotificationSettingsFromDB();
            int numberOfNotifications = notificationSettings.getNotificationNumber();

            for (int i = 0; i<numberOfNotifications; i++) {

                Week week = notificationSettings.getWeek();
                LocalDate now;

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                //Toast.makeText(getApplicationContext(), calendar.getTime().toString(), Toast.LENGTH_LONG).show();
                calendar.add(Calendar.SECOND, 2);
                //Toast.makeText(getApplicationContext(), calendar.getTime().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), NotificationsService.class);
                PendingIntent pendingIntent = PendingIntent.getService(
                        getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
                /*AlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    now = LocalDate.now();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (week.isMonday() == true && now.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isTuesday() == true && now.getDayOfWeek().equals(DayOfWeek.TUESDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isWednesday() == true && now.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isThursday() == true && now.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isFriday() == true && now.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isSaturday() == true && now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (week.isSunday() == true && now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                            calendar.add(Calendar.SECOND, 1);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        }

                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    static  void onNotificationRemoved (){

    }




    public NotificationSettings getNotificationSettingsFromDB() {
        NotificationSettings notificationSettings = new NotificationSettings();
        Cursor res = myDB.getNotificationSettings();

        while (res.moveToNext()) {
            notificationSettings.setNotificationNumber(Integer.parseInt(res.getString(1)));
            setDay(Day.MONDAY, Integer.parseInt((res.getString(2))), notificationSettings);
            setDay(Day.TUESDAY, Integer.parseInt((res.getString(3))), notificationSettings);
            setDay(Day.WEDNESDAY, Integer.parseInt((res.getString(4))), notificationSettings);
            setDay(Day.THURSDAY, Integer.parseInt((res.getString(5))), notificationSettings);
            setDay(Day.FRIDAY, Integer.parseInt((res.getString(6))), notificationSettings);
            setDay(Day.SATURDAY, Integer.parseInt((res.getString(7))), notificationSettings);
            setDay(Day.SUNDAY, Integer.parseInt((res.getString(8))), notificationSettings);
        }
        return notificationSettings;
    }

    private void setDay(Day day, int booleanAsInt, NotificationSettings notificationSettings) {
        Week week = notificationSettings.getWeek();
        if (booleanAsInt == 0) {
            week.setDay(day, false);
        } else {
            week.setDay(day, true);
        }
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
