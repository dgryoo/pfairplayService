package model.enumfield;

public enum DayOfWeek {

    일(1),
    월(2),
    화(3),
    수(4),
    목(5),
    금(6),
    토(7);

    private int dayOfWeekNo;

    DayOfWeek(int dayOfWeekNo) {
        this.dayOfWeekNo = dayOfWeekNo;
    }

    public int getDayOfWeekNo() {
        return dayOfWeekNo;
    }

    public static DayOfWeek from(int dayOfWeekNo) {
        for (DayOfWeek dow : DayOfWeek.values()) {
            if (dow.dayOfWeekNo == dayOfWeekNo) {
                return dow;
            }
        }
        return null;
    }

    public static DayOfWeek from(String value) {
        for (DayOfWeek dow :DayOfWeek.values()) {
           if(dow.name().equals(value)) {
               return dow;
           }
        }
        return null;
    }

}
