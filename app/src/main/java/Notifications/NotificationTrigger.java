package Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

import com.example.myapplication.DatabaseHelper;

import java.time.DayOfWeek;
import java.time.LocalDate;

import EasyKnowLib.Day;
import EasyKnowLib.NotificationSettings;
import EasyKnowLib.Week;

public class NotificationTrigger extends BroadcastReceiver {
    public static NotificationSettings notificationSettings;
    public static int numberOfNotifications;
    private DatabaseHelper myDB;
    @Override
    public void onReceive(Context context, Intent intent) {
        myDB = new DatabaseHelper(context);
        NotificationsService.currentNumberOfNotifications = 0;
//      NotificationsService.notificationID = 2;
//      NotificationsService.requestCode = 0;
        notificationSettings = getNotificationSettingsFromDB();
        numberOfNotifications = notificationSettings.getNotificationNumber();

        Week week = notificationSettings.getWeek();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            LocalDate now = LocalDate.now();

            if (week.isMonday() == true && now.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                setIntentNotification(context);
            } else if (week.isTuesday() == true && now.getDayOfWeek().equals(DayOfWeek.TUESDAY)) {
                setIntentNotification(context);
            } else if (week.isWednesday() == true && now.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
                setIntentNotification(context);
            } else if (week.isThursday() == true && now.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                setIntentNotification(context);
            } else if (week.isFriday() == true && now.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                setIntentNotification(context);
            } else if (week.isSaturday() == true && now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                setIntentNotification(context);
            } else if (week.isSunday() == true && now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                setIntentNotification(context);
            }
        }


    }

    private void setIntentNotification(Context context){
        Intent intentNotification = new Intent(context, NotificationsService.class);
        NotificationsService.enqueueWork(context, intentNotification);
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
}
