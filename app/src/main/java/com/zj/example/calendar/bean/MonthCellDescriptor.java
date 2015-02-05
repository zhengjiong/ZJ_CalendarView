package com.zj.example.calendar.bean;

import org.joda.time.LocalDate;

/**
 * 日
 * Created by zhengjiong on 2015/2/2.
 */
public class MonthCellDescriptor {

    private final LocalDate date;
    private final String value;//第幾日,23
    private final boolean isCurrentMonth;//用於判斷改日期是否屬於當前月,不是當月的顏色設置為灰色
    private boolean isSelected;
    private final boolean isToday;
    private final boolean isSelectable;


    public MonthCellDescriptor(LocalDate date, String value, boolean isCurrentMonth,
                boolean isSelected, boolean isToday, boolean isSelectable) {
        this.date = date;
        this.value = value;
        this.isCurrentMonth = isCurrentMonth;
        this.isSelected = isSelected;
        this.isToday = isToday;
        this.isSelectable = isSelectable;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    @Override
    public String toString() {
        return "MonthCellDescriptor{" +
                "date=" + date +
                ", value=" + value +
                ", isCurrentMonth=" + isCurrentMonth +
                ", isSelected=" + isSelected +
                ", isToday=" + isToday +
                ", isSelectable=" + isSelectable +
                '}';
    }
}
