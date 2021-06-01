package Notifications;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.RemoteInput;

import com.example.myapplication.*;


import java.util.ArrayList;
import java.util.List;

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
    static String userAnswer;
    static List<String> Messages = new ArrayList<>();
    static boolean finished;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            //choiceNotificationStyle();
            //messageNotificationStyleSender(getApplicationContext());
            if(intent.hasExtra("finished")){
                finished = true;
            } else{ finished = false;}
            messageNotificationStyle();
        }

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

    public void choiceNotificationStyle() {
        String word = "random word"; //Get the next word to check from DB

        String meaning = "meaning"; //Get its meaning

        Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "This is a message");
        PendingIntent actionIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Convert image type to bitmap
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("Notification")
                .setContentText(Html.fromHtml("Do you know what "+"<b>" + word +"</b>"+" means?"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setShowWhen(false)
                .setColor(Color.BLUE)
                .setLargeIcon(icon)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(Html.fromHtml("<b>"+word+"</b>"+" means "+"<b>"+meaning+"</b>"))
                        .setBigContentTitle("Did you get it correct?")
                        .setSummaryText("Summary Text")
                )
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "YES", actionIntent)
                .addAction(R.mipmap.ic_launcher, "NO", actionIntent)

                .build();

        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, notification);
    }

    public void messageNotificationStyle(){
        String word = "random word"; //Get the next word to check from DB
        String meaning = "meaning"; //Get its meaning

        String sText = new String(String.valueOf(Html.fromHtml("Do you know what <b>" + word +"</b> means?")));
        Messages.add(sText );
        messageNotificationStyleSender(this);
    }

    public static void messageNotificationStyleSender(Context context) {


//        Intent activityIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, activityIntent, 0);
//



        Bundle bundle = new Bundle();
        bundle.putString("notification_result", "No");
         RemoteInput remoteInput = new RemoteInput.Builder("key_text_answer")
            .setLabel("Your answer...")
            //.setChoices(new String[]{new String("No")})
            //.addExtras(bundle)
            .build();

        Intent answerIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent answerPendingIntent = PendingIntent.getBroadcast(context,
                0, answerIntent,0);

        NotificationCompat.Action answerAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_baseline_done_24,
                Html.fromHtml("<b>CHECK</b>"),
                answerPendingIntent
        ).addRemoteInput(remoteInput).build();


        //Check if the user didn't write anything and clicked on NO button
        //Bundle results = RemoteInput.getResultsFromIntent(answerIntent);
//        if(remoteInput == null) {
//            answerIntent.putExtra("notification_result","NO");
//        }

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("");
        //messagingStyle.setConversationTitle(Html.fromHtml("Do you know what <b>" + word +"</b> means?"));





        for(String message: Messages) {
            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            message,
                            System.currentTimeMillis(),
                            ""
                    );
            messagingStyle.addMessage(notificationMessage);
        }

        //Convert image type to bitmap
        //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground);

        // lets set timeoutAfter 5 seconds if it is finished

        if(finished = true) {
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_message)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    //.setLargeIcon(icon)
                    .setStyle(messagingStyle)
                    .addAction(answerAction)
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .addAction(R.mipmap.ic_launcher, "NO", answerPendingIntent)
                    .setTimeoutAfter(5000)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(2, notification);
        } else {
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_message)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    //.setLargeIcon(icon)
                    .setStyle(messagingStyle)
                    .addAction(answerAction)
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .addAction(R.mipmap.ic_launcher, "NO", answerPendingIntent)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(2, notification);
        }
    }
}
