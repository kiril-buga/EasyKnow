package EasyKnowLib;

public class LearnItemStatus extends LearnStatus {
    private int numberOfSuccessfulAnswers;
    private int numberOfUnsuccessfulAnswers;
    private boolean LastTestSuccessful;

    public int getNumberOfSuccessfulAnswers() {
        return numberOfSuccessfulAnswers;
    }

    public void setNumberOfSuccessfulAnswers(int numberOfSuccessfulAnswers) {
        this.numberOfSuccessfulAnswers = numberOfSuccessfulAnswers;
    }

    public int getNumberOfUnsuccessfulAnswers() {
        return numberOfUnsuccessfulAnswers;
    }

    public void setNumberOfUnsuccessfulAnswers(int numberOfUnsuccessfulAnswers) {
        this.numberOfUnsuccessfulAnswers = numberOfUnsuccessfulAnswers;
    }

    public boolean isLastTestSuccessful() {
        return LastTestSuccessful;
    }

    public void setLastTestSuccessful(boolean lastTestSuccessful) {
        LastTestSuccessful = lastTestSuccessful;
    }
}
