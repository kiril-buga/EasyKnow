package EasyKnowLib;

import java.util.ArrayList;

public class LearnFolder {
    private String title;
    private ArrayList<LearnItem> learnItems;
    private LearnFolderStatus learnFolderStatus;
    private NotificationStatus notificationStatus;

    public LearnFolder(String title) {
        this.title = title;
        this.learnItems = new ArrayList<>();
        this.learnFolderStatus = new LearnFolderStatus();
        this.notificationStatus = new NotificationStatus();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<LearnItem> getLearnItems() {
        return learnItems;
    }

    public void setLearnItems(ArrayList<LearnItem> learnItems) {
        this.learnItems = learnItems;
    }

    public LearnFolderStatus getLearnFolderStatus() {
        return learnFolderStatus;
    }

    public void setLearnFolderStatus(LearnFolderStatus learnFolderStatus) {
        this.learnFolderStatus = learnFolderStatus;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
