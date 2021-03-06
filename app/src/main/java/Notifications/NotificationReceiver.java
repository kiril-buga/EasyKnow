package Notifications;

import android.app.Notification.MessagingStyle.Message;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("toastMessage")) {
            String message = intent.getStringExtra("toastMessage");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        String meaning;
        String word;
        String action = intent.getAction();

        //Remote Input
        Bundle remoteInput = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            remoteInput = RemoteInput.getResultsFromIntent(intent);
        }
        if(remoteInput != null && intent.hasExtra("word") && intent.hasExtra("meaning")) {
            CharSequence answerText = remoteInput.getCharSequence("key_text_answer");
            word = intent.getStringExtra("word");
            meaning = intent.getStringExtra("meaning");
            //Message answer = new Message(answerText, null);
            String reply;
            if(answerText.equals(meaning)){
                reply = new String("Congratulations! " + answerText + " is the correct answer.");

                NotificationsService.successfulAnswer(context);
            } else {
                reply = new String("Sorry "+word+" means " + meaning+ ", try next time");

                NotificationsService.wrongAnswer(context);
            }
            intent = new Intent(context, NotificationsService.class);
            intent.putExtra("finished","true");
            NotificationsService.Messages.add(reply);
            //Let's update the notification when received
            NotificationsService.showResults(context);

        } else if (action.equals("NO_ACTION") && intent.hasExtra("word") && intent.hasExtra("meaning")){
            word = intent.getStringExtra("word");
            meaning = intent.getStringExtra("meaning");
            String reply = new String(word+" means " + meaning);
            NotificationsService.wrongAnswer(context);

            intent = new Intent(context, NotificationsService.class);
            intent.putExtra("finished","true");
            NotificationsService.Messages.add(reply);
            //Let's update the notification when received
            NotificationsService.showResults(context);
        }

        Toast.makeText(context, "Notification Receiver",Toast.LENGTH_LONG).show();
        //Intent intent1 = new Intent(context, NotificationsService.class);
        //context.startService(intent1);
    }
}
