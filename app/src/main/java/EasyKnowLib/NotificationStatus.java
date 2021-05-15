package EasyKnowLib;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class NotificationStatus {
    private ArrayList<DayOfWeek> notificationDays;
    private LocalTime notificationTime;
    private LocalDateTime lastNotificationTime;
    private int numberOfTimesUserNotified;

    public ArrayList<DayOfWeek> getNotificationDays() {
        return notificationDays;
    }

    public void setNotificationDays(ArrayList<DayOfWeek> notificationDays) {
        this.notificationDays = notificationDays;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public LocalDateTime getLastNotificationTime() {
        return lastNotificationTime;
    }

    public void setLastNotificationTime(LocalDateTime lastNotificationTime) {
        this.lastNotificationTime = lastNotificationTime;
    }

    public int getNumberOfTimesUserNotified() {
        return numberOfTimesUserNotified;
    }

    public void setNumberOfTimesUserNotified(int numberOfTimesUserNotified) {
        this.numberOfTimesUserNotified = numberOfTimesUserNotified;
    }
}
