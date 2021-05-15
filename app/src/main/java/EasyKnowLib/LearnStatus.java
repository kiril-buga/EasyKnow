package EasyKnowLib;

import java.time.LocalDateTime;

public abstract class LearnStatus {
    private LocalDateTime lastTestTime;
    private int numberOfViews;
    private boolean learned;

    public void setLastTestTime(LocalDateTime lastTestTime) {
        this.lastTestTime = lastTestTime;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public LocalDateTime getLocalDateTime() {
        return lastTestTime;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public boolean isLearned() {
        return learned;
    }
}
