package Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.*;


import static com.example.myapplication.EasyKnow.CHANNEL_1_ID;



public class NotificationsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public NotificationsService() {
        super("My new Intent Service");
    }

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "This is a message");
        PendingIntent actionIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Convert image type to bitmap
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Notification")
                .setContentText("This is a notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setLargeIcon(icon)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("This is a random long text. Bla Bla BlaThis is a random long text. Bla Bla BlaThis is a random long text. Bla Bla BlaThis is a random long text. Bla Bla BlaThis is a random long text. Bla Bla BlaThis is a random long text. Bla Bla BlaThis is a random long text. Bla Bla Bla")
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text"))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(3, notification);
        //startService();
    }

    //Foreground service
    public void startService() {
        String input = "Just a sentence";

        Intent serviceIntent = new Intent(this, Services.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, Services.class);
        stopService(serviceIntent);
    }
}
