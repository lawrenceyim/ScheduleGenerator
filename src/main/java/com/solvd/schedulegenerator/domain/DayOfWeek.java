package com.solvd.schedulegenerator.domain;

import java.util.Arrays;

public enum DayOfWeek {

    MONDAY("Monday", 1),
    TUESDAY("Tuesday", 2),
    WEDNESDAY("Wednesday", 3),
    THURSDAY("Thursday", 4),
    FRIDAY("Friday", 5),
    SATURDAY("Saturday", 6),
    SUNDAY("Sunday", 7);

    private String displayName;
    private int number;

    DayOfWeek(String displayName, int number) {
        this.displayName = displayName;
        this.number = number;
    }

    public static DayOfWeek valueOf(int value) {
        return Arrays.stream(values()).filter((dayOfWeek) -> dayOfWeek.number == value).findFirst().orElse(null);
    }
}
