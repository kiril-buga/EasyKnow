package EasyKnowLib;

public class Week {
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    public void setDay(Day day, boolean value) {
        switch (day) {
            case MONDAY:
                monday = value;
                break;
            case TUESDAY:
                tuesday = value;
                break;
            case WEDNESDAY:
                wednesday = value;
                break;
            case THURSDAY:
                thursday = value;
                break;
            case FRIDAY:
                friday = value;
                break;
            case SATURDAY:
                saturday = value;
                break;
            case SUNDAY:
                sunday = value;
                break;
        }
    }

    public void setSelectedDays(Day[] days, boolean value) {
        for (Day day : days) {
            setDay(day, value);
        }
    }

}
