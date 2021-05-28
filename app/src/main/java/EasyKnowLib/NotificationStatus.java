package EasyKnowLib;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class NotificationStatus {
    private Week week;
    private int notificationNumber = 0;
    private LocalTime notificationTime;
    private LocalDateTime lastNotificationTime;
    private int numberOfTimesUserNotified;

    public NotificationStatus() {
        this.week = new Week();
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Week getWeek() {
        return week;
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

    public int getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        this.notificationNumber = notificationNumber;
    }
}
