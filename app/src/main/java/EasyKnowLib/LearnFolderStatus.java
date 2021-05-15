package EasyKnowLib;

public class LearnFolderStatus extends LearnStatus {
    private int numberOfKnownWords;
    private int numberOfUnknownWords;

    public int getNumberOfKnownWords() {
        return numberOfKnownWords;
    }

    public void setNumberOfKnownWords(int numberOfKnownWords) {
        this.numberOfKnownWords = numberOfKnownWords;
    }

    public int getNumberOfUnknownWords() {
        return numberOfUnknownWords;
    }

    public void setNumberOfUnknownWords(int numberOfUnknownWords) {
        this.numberOfUnknownWords = numberOfUnknownWords;
    }

}
