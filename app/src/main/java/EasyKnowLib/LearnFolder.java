package EasyKnowLib;

import java.util.ArrayList;

public class LearnFolder {
    private String folderTitle;
    private ArrayList<LearnItem> learnItems;
    private LearnFolderStatus learnFolderStatus;
    private NotificationStatus notificationStatus;

    public LearnFolder(String folderTitle) {
        this.folderTitle = folderTitle;
        this.learnItems = new ArrayList<>();
        this.learnFolderStatus = new LearnFolderStatus();
        this.notificationStatus = new NotificationStatus();
    }

    public String getFolderTitle() {
        return folderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
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
