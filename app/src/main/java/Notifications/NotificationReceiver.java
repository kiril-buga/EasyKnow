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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("toastMessage")) {
            String message = intent.getStringExtra("toastMessage");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        //Remote Input
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if(remoteInput != null) {
            CharSequence answerText = remoteInput.getCharSequence("key_text_answer");
            //Message answer = new Message(answerText, null);
            String reply;
            if(answerText.equals("meaning")){
                reply = new String("Congratulations! " + answerText + " is the correct answer.");
            } else {
                reply = new String(String.valueOf(Html.fromHtml("Sorry <b>random word</b> means <b>meaning</b>. Try next time")));
            }
            intent = new Intent(context, NotificationsService.class);
            intent.putExtra("finished","true");
            NotificationsService.Messages.add(reply);
            //Let's update the notification when received
            NotificationsService.messageNotificationStyleSender(context);

        }

        Toast.makeText(context, "Notification Receiver",Toast.LENGTH_LONG).show();
        //Intent intent1 = new Intent(context, NotificationsService.class);
        //context.startService(intent1);
    }
}
