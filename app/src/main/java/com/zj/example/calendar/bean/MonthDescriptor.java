package com.zj.example.calendar.bean;

/**
 * 月
 * Created by zhengjiong on 2015/2/2.
 */
public class MonthDescriptor {

    private final int month;
    private final int year;
    private final String lable;


    public MonthDescriptor(int month, int year, String lable) {
        this.month = month;
        this.year = year;
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "MonthDescriptor{" +
                "month=" + month +
                ", year=" + year +
                ", lable='" + lable + '\'' +
                '}';
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getLable() {
        return lable;
    }
}
