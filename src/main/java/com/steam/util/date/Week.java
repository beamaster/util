package com.steam.util.date;

/**
 * 星期
 *
 * @author 段
 */
public enum Week {
    MONDAY("星期一", "Monday", "Mon.", 1),
    TUESDAY("星期二", "Tuesday", "Tues.", 2),
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
    THURSDAY("星期四", "Thursday", "Thur.", 4),
    FRIDAY("星期五", "Friday", "Fri.", 5),
    SATURDAY("星期六", "Saturday", "Sat.", 6),
    SUNDAY("星期日", "Sunday", "Sun.", 7);

    String cnName;
    String enName;
    String enShortName;
    int number;

    Week(String cnName, String enName, String enShortName, int number) {
        this.cnName = cnName;
        this.enName = enName;
        this.enShortName = enShortName;
        this.number = number;
    }

    public String getChineseName() {
        return cnName;
    }

    public String getName() {
        return enName;
    }

    public String getShortName() {
        return enShortName;
    }

    public int getNumber() {
        return number;
    }
}
