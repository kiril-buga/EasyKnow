package Notifications;

import java.util.*;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.*;

import EasyKnowLib.Day;
import EasyKnowLib.Week;

import static com.example.myapplication.EasyKnow.CHANNEL_0_ID;

public class Services extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_0_ID)
                .setContentTitle("Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_message)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
        checkTime();

        return START_NOT_STICKY; //START_REDELIVER_INTENT
    }

    private void checkTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        Toast.makeText(getApplicationContext(), calendar.getTime().toString(),Toast.LENGTH_LONG).show();
        calendar.add(Calendar.SECOND, 10);
        Toast.makeText(getApplicationContext(), calendar.getTime().toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager AlarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        AlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                1000 * 60 * 60 * 24, pendingIntent);


        //Toast.makeText(this, calendar.toString(),Toast.LENGTH_LONG).show();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
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
