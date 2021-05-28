package Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra("toastMessage")) {
            String message = intent.getStringExtra("toastMessage");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, "Notification Receiver",Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(context, NotificationsService.class);
        context.startService(intent1);
    }
}
