package EasyKnowLib;

import java.time.LocalDateTime;

public class Word {
    // WordData
    private int id;
    private String title;
    private String meaning;
    // TestData
    private LocalDateTime lastNotificationTime;
    private boolean LastTestSuccessful;
    private int learnStatus = 0;
    private int folderId;

    public Word(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
    }

    public Word(int id, String title, String meaning, LocalDateTime lastNotificationTime, boolean lastTestSuccessful, int learnStatus, int folderId) {
        this.id = id;
        this.title = title;
        this.meaning = meaning;
        this.lastNotificationTime = lastNotificationTime;
        LastTestSuccessful = lastTestSuccessful;
        this.learnStatus = learnStatus;
        this.folderId = folderId;
    }

    public boolean incrementlearnStatus() {
        boolean incremented = false;
        if (learnStatus < 5) {
            learnStatus++;
            incremented = true;
        }
        return incremented;
    }

    public boolean decrementLearnStatus() {
        boolean decremented = false;
        if (learnStatus > 0) {
            learnStatus--;
            decremented = true;
        }
        return decremented;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public LocalDateTime getLastNotificationTime() {
        return lastNotificationTime;
    }

    public void setLastNotificationTime(LocalDateTime lastNotificationTime) {
        this.lastNotificationTime = lastNotificationTime;
    }

    public boolean isLastTestSuccessful() {
        return LastTestSuccessful;
    }

    public void setLastTestSuccessful(boolean lastTestSuccessful) {
        LastTestSuccessful = lastTestSuccessful;
    }

    public int getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(int learnStatus) {
        this.learnStatus = learnStatus;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
