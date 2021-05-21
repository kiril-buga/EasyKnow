package EasyKnowLib;

public class LearnItemStatus extends LearnStatus {

    private int numberOfSuccessfulAnswers;
    private boolean LastTestSuccessful;

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
