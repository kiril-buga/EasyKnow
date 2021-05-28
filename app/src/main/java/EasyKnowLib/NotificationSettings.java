package EasyKnowLib;

public class NotificationSettings {
    private int notificationNumber = 0;
    private Week week;

    public NotificationSettings() {
        this.week = new Week();
    }

    public int getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}
