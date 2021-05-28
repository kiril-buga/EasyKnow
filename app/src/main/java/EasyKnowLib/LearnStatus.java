package EasyKnowLib;

import java.time.LocalDateTime;

public abstract class LearnStatus {
    private LocalDateTime lastTestTime;
    private boolean learned;
    private int numberOfSuccessfulAnswers;
    private boolean LastTestSuccessful;

    public LocalDateTime getLastTestTime() {
        return lastTestTime;
    }

    public void setLastTestTime(LocalDateTime lastTestTime) {
        this.lastTestTime = lastTestTime;
    }

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public int getNumberOfSuccessfulAnswers() {
        return numberOfSuccessfulAnswers;
    }

    public void setNumberOfSuccessfulAnswers(int numberOfSuccessfulAnswers) {
        this.numberOfSuccessfulAnswers = numberOfSuccessfulAnswers;
    }

    public boolean isLastTestSuccessful() {
        return LastTestSuccessful;
    }

    public void setLastTestSuccessful(boolean lastTestSuccessful) {
        LastTestSuccessful = lastTestSuccessful;
    }
}
