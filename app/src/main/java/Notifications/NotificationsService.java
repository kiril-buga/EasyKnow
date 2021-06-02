package Notifications;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.RemoteInput;

import com.example.myapplication.*;
import EasyKnowLib.*;


import java.time.LocalDateTime;
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
    static DatabaseHelper myDB;
    private WordFinder wordFinder;

    static String word;
    static String meaning;
    static Word obWord;




    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //Database
        myDB = new DatabaseHelper(this);

        wordFinder = new WordFinder();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            //choiceNotificationStyle();
//            NotificationSettings notificationSettings = getNotificationSettingsFromDB();
//            int numberOfNotifications = notificationSettings.getNotificationNumber();

            Messages.clear();

            messageNotificationStyle();
        }

        //startService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Services.notificationDestroyed = true;
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

    public static void successfulAnswer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            obWord.incrementlearnStatus();

            obWord.setLastNotificationTime(LocalDateTime.now());
            myDB.updateWord(obWord.getId(), obWord.getLastNotificationTime().toString(), obWord.getLearnStatus());
        }
    }
    public static void wrongAnswer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            obWord.decrementLearnStatus();

            obWord.setLastNotificationTime(LocalDateTime.now());

            myDB.updateWord(obWord.getId(), obWord.getLastNotificationTime().toString(), obWord.getLearnStatus());
        }
    }

    public static void showResults(Context context){

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_answer")
                .setLabel("Your answer...")
                //.setChoices(new String[]{new String("No")})
                //.addExtras(bundle)
                .build();


        Intent answerIntent = new Intent(context, NotificationReceiver.class);
        answerIntent.putExtra("word",word);
        answerIntent.putExtra("meaning",meaning);
        PendingIntent answerPendingIntent = PendingIntent.getBroadcast(context,
                0, answerIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action answerAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_baseline_done_24,
                Html.fromHtml("<b>CHECK</b>"),
                answerPendingIntent
        ).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle("");

        for(String message: Messages) {
            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            message,
                            System.currentTimeMillis(),
                            ""
                    );
            messagingStyle.addMessage(notificationMessage);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setLargeIcon(icon)
                .setStyle(messagingStyle)
                //.addAction(answerAction)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                //.addAction(R.mipmap.ic_launcher, "NO", answerPendingIntent)
                .setTimeoutAfter(10000)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, notification);
    }


   public void messageNotificationStyle() {
        obWord = wordFinder.getWord(myDB);

        word = obWord.getTitle(); //Get the next word to check from DB
        meaning = obWord.getMeaning(); //Get its meaning

        finished = false;

        String sText = new String(String.valueOf(Html.fromHtml("Do you know what <b>" + word +"</b> means?")));
        Messages.add(sText );
        messageNotificationStyleSender(this);
    }

    public static void messageNotificationStyleSender(Context context) {
//        Intent activityIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_answer")
            .setLabel("Your answer...")
            //.setChoices(new String[]{new String("No")})
            //.addExtras(bundle)
            .build();


        Intent answerIntent = new Intent(context, NotificationReceiver.class);
        answerIntent.putExtra("word",word);
        answerIntent.putExtra("meaning",meaning);
        PendingIntent answerPendingIntent = PendingIntent.getBroadcast(context,
                0, answerIntent,PendingIntent.FLAG_UPDATE_CURRENT);


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

//        if(finished = true) {
//            Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
//                    .setSmallIcon(R.drawable.ic_message)
//                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                    .setShowWhen(false)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    //.setLargeIcon(icon)
//                    .setStyle(messagingStyle)
//                    .addAction(answerAction)
//                    .setColor(Color.BLUE)
//                    .setAutoCancel(true)
//                    .setOnlyAlertOnce(true)
//                    .addAction(R.mipmap.ic_launcher, "NO", answerPendingIntent)
//                    .setTimeoutAfter(15000)
//                    .build();
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//            notificationManager.notify(2, notification);
//        }
        Intent noReceive = new Intent(context, NotificationReceiver.class);
        noReceive.putExtra("word",word);
        noReceive.putExtra("meaning",meaning);
        noReceive.setAction("NO_ACTION");
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(context,
                0, noReceive,PendingIntent.FLAG_UPDATE_CURRENT);

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
                .addAction(R.mipmap.ic_launcher, "NO", pendingIntentNo)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, notification);

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

    //for ID
//    public NotificationSettings getNotificationSettingsFromDB() {
//        NotificationSettings notificationSettings = new NotificationSettings();
//        Cursor res = myDB.getNotificationSettings();
//
//        while (res.moveToNext()) {
//            notificationSettings.setNotificationNumber(Integer.parseInt(res.getString(1)));
//            setDay(Day.MONDAY, Integer.parseInt((res.getString(2))), notificationSettings);
//            setDay(Day.TUESDAY, Integer.parseInt((res.getString(3))), notificationSettings);
//            setDay(Day.WEDNESDAY, Integer.parseInt((res.getString(4))), notificationSettings);
//            setDay(Day.THURSDAY, Integer.parseInt((res.getString(5))), notificationSettings);
//            setDay(Day.FRIDAY, Integer.parseInt((res.getString(6))), notificationSettings);
//            setDay(Day.SATURDAY, Integer.parseInt((res.getString(7))), notificationSettings);
//            setDay(Day.SUNDAY, Integer.parseInt((res.getString(8))), notificationSettings);
//        }
//        return notificationSettings;
//    }
//    private void setDay(Day day, int booleanAsInt, NotificationSettings notificationSettings) {
//        Week week = notificationSettings.getWeek();
//        if (booleanAsInt == 0) {
//            week.setDay(day, false);
//        } else {
//            week.setDay(day, true);
//        }
//    }
}
