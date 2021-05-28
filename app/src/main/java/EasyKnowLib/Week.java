package EasyKnowLib;

public class Week {
    private boolean monday = false;
    private boolean tuesday = false;
    private boolean wednesday = false;
    private boolean thursday = false;
    private boolean friday = false;
    private boolean saturday = false;
    private boolean sunday = false;

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

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }
}
