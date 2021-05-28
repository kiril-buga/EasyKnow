package EasyKnowLib;

import java.time.LocalDateTime;

public class Word {
    // WordData
    private String title;
    private String meaning;
    // TestData
    private LocalDateTime lastNotificationTime;
    private boolean LastTestSuccessful;
    private int numberOfSuccessfulAnswers = 0;
    public Word(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
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

    public LocalDateTime getLastTestTime() {
        return lastNotificationTime;
    }

    public void setLastTestTime(LocalDateTime lastTestTime) {
        this.lastNotificationTime = lastTestTime;
    }

    public boolean isLastTestSuccessful() {
        return LastTestSuccessful;
    }

    public void setLastTestSuccessful(boolean lastTestSuccessful) {
        LastTestSuccessful = lastTestSuccessful;
    }

    public int getNumberOfSuccessfulAnswers() {
        return numberOfSuccessfulAnswers;
    }

    public void setNumberOfSuccessfulAnswers(int numberOfSuccessfulAnswers) {
        this.numberOfSuccessfulAnswers = numberOfSuccessfulAnswers;
    }

}
