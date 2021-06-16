package com.example.pfairplayservice.model.enumfield;

public enum Weekday {

    일(1),
    월(2),
    화(3),
    수(4),
    목(5),
    금(6),
    토(7);

    private int weekdayNum;

    Weekday(int weekdayNum) {
        this.weekdayNum = weekdayNum;
    }

    public int getWeekdayNum() {
        return weekdayNum;
    }

    public static Weekday from(int weekdayNum) {
        for (Weekday wd : Weekday.values()) {
            if (wd.weekdayNum == weekdayNum) {
                return wd;
            }
        }
        return null;
    }



}
