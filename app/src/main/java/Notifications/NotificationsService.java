package Notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

import com.example.myapplication.AddWordActivity;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import EasyKnowLib.Day;
import EasyKnowLib.NotificationSettings;
import EasyKnowLib.Week;
import EasyKnowLib.Word;
import EasyKnowLib.WordFinder;

import static com.example.myapplication.EasyKnow.CHANNEL_1_ID;


@SuppressLint("OverrideAbstract")
public class NotificationsService extends JobIntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public NotificationsService() {
        super();
    }

    private NotificationManagerCompat notificationManager;
    static String userAnswer;
    static List<String> Messages = new ArrayList<>();
    static boolean finished;
    static DatabaseHelper myDB;
    private WordFinder wordFinder;
    static int notificationID = 2;
    static int requestCode = 0;
    static int currentNumberOfNotifications = 0;

    static String word;
    static String meaning;
    static Word obWord;

//    static NotificationSettings notificationSettings = getNotificationSettingsFromDB();
//    static int numberOfNotifications = notificationSettings.getNotificationNumber();


    private static final String TAG = "MyJobIntentService";
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 2;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, NotificationsService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, NotificationsService.class);
        //currentNumberOfNotifications = 0;
        //enqueueWork(getApplicationContext(), intent);

        Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_LONG);
//        showToast("Job Execution Started");
//
//        myDB = new DatabaseHelper(this);
//
//        wordFinder = new WordFinder();
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
//            //choiceNotificationStyle();
//            //            NotificationSettings notificationSettings = getNotificationSettingsFromDB();
//            //            int numberOfNotifications = notificationSettings.getNotificationNumber();
//
//            Messages.clear();
//
//            messageNotificationStyle();
//        }
    }

    void showToast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NotificationsService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

//      Database
        myDB = new DatabaseHelper(this);
        wordFinder = new WordFinder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            //choiceNotificationStyle();
            //            NotificationSettings notificationSettings = getNotificationSettingsFromDB();
            //            int numberOfNotifications = notificationSettings.getNotificationNumber();
            Messages.clear();
            messageNotificationStyle();

            stopSelf();
        }
        //startService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showToast("Job Execution Finished");
    }

    @SuppressWarnings("deprecation")
    final Handler mHandler = new Handler();



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
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                //.addAction(R.mipmap.ic_launcher, "NO", answerPendingIntent)
                .setTimeoutAfter(5000)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationID, notification);


//let's call another notification


        Handler mainHandler = new Handler(context.getMainLooper());
        notificationID++;
        requestCode++;
        currentNumberOfNotifications++; 
        if(NotificationTrigger.numberOfNotifications>currentNumberOfNotifications) {


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            //Toast.makeText(getApplicationContext(), calendar.getTime().toString(), Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), calendar.getTime().toString(), Toast.LENGTH_LONG).show();


//            PendingIntent pendingIntent = PendingIntent.getService(
//                    context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            //calendar.add(Calendar.SECOND, 1);
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            //context.startService(intent);

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(context, NotificationTrigger.class);
                    NotificationsService.enqueueWork(context, intent);




                } // This is your code
            };
            mainHandler.post(myRunnable);
        }
    }

   public void messageNotificationStyle() {
        obWord = wordFinder.getWord(myDB);

        word = obWord.getTitle(); //Get the next word to check from DB
        meaning = obWord.getMeaning(); //Get its meaning

        //finished = false;

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
//            notificationManager.notify(notificationID, notification);
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
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "NO", pendingIntentNo)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationID, notification);

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
        notificationManager.notify(notificationID, notification);
    }

    //for ID
//    public static NotificationSettings getNotificationSettingsFromDB() {
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
//    private static void setDay(Day day, int booleanAsInt, NotificationSettings notificationSettings) {
//        Week week = notificationSettings.getWeek();
//        if (booleanAsInt == 0) {
//            week.setDay(day, false);
//        } else {
//            week.setDay(day, true);
//        }
//    }
}
