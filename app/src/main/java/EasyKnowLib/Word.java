package EasyKnowLib;

public class Word {
    private String title;
    private String meaning;
    private LearnItemStatus learnStatus;

    public Word(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
        this.learnStatus = new LearnItemStatus();
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

    public LearnItemStatus getLearnStatus() {
        return learnStatus;
    }

    public void setLearnStatus(LearnItemStatus learnStatus) {
        this.learnStatus = learnStatus;
    }
}
