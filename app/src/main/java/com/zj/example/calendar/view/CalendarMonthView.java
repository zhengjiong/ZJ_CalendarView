package com.zj.example.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zj.example.calendar.R;
import com.zj.example.calendar.bean.MonthCellDescriptor;
import com.zj.example.calendar.bean.MonthDescriptor;

import java.util.List;

/**
 * create by zhengjiong
 * Date: 2015-01-17
 * Time: 11:03
 */
public class CalendarMonthView extends LinearLayout{
    private CalendarGridView mGridView;
    private DayClickListener mDayClickListener;

    public CalendarMonthView(Context context) {
        super(context);
    }

    public CalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface DayClickListener{
        public void handleClick(MonthCellDescriptor cell);
    }

    /**
     * 這裡沒有弄懂爲什麽要使用靜態創建,
     * 直接寫到layout的應該也可以,後面再
     * 測試兩種方式的差別
     */
    /*public static CalendarMonthView create(Context context, DayClickListener listener, int dayTextColorResId) {
        CalendarMonthView monthView = (CalendarMonthView) LayoutInflater.from(context).inflate(R.layout.month, null);
        monthView.setDayTextColor(dayTextColorResId);
        monthView.mDayClickListener = listener;
        return monthView;
    }*/

//    public void init2(LocalDate month) {
//        DateTimeFormatter formatter = DateTimeFormat.forPattern("d");
//
//        /**
//         * 下面兩種方法都可以得到月份的第一天
//         * LocalDate month1 = month.dayOfMonth().withMinimumValue();
//         * LocalDate month2 = month.withDayOfMonth(1);
//         */
//        if (month.withDayOfMonth(1).getDayOfWeek() == DateTimeConstants.SUNDAY) {
//            month = month.withDayOfMonth(1);
//        } else {
//            //如果本月的第一天不是星期天,則顯示上一個星期
//            month = month.withDayOfMonth(1).minusWeeks(1).withDayOfWeek(7);
//        }
//
//        for (int i = 1, weekNum = mGridView.getChildCount(); i < weekNum; i++) {
//            CalendarRowView rowView = (CalendarRowView) mGridView.getChildAt(i);
//
//            for (int j = 0, cellNum = rowView.getChildCount(); j < cellNum; j++) {
//                CalendarCellView cellView = (CalendarCellView) rowView.getChildAt(j);
//                cellView.setText(month.toString(formatter));
//                month = month.plusDays(1);
//            }
//        }
//    }

    public void init(DayClickListener listener, MonthDescriptor mMonth, List<List<MonthCellDescriptor>> mCells) {
        for (int i = 0; i < mGridView.getChildCount(); i++) {
            //獲取周視圖
            CalendarRowView weekView = (CalendarRowView) mGridView.getChildAt(i);

            //設置每一天的點擊事件
            weekView.setListener(listener);

            List<MonthCellDescriptor> weekData = mCells.get(i);

            for (int j = 0; j < weekView.getChildCount(); j++) {
                CalendarCellView cellView = (CalendarCellView) weekView.getChildAt(j);
                MonthCellDescriptor cellDescriptor = weekData.get(j);

                if (cellDescriptor.isSelected()) {
                    cellView.setTextColor(0xFFFF00B5);
                } else {
                    cellView.setTextColor(0xFF333333);
                }
                if (cellDescriptor.isToday()) {
                    cellView.setTextColor(0xFFFF4D4D);
                    //這裡要修改為cellview.setToday
                }
                if (!cellDescriptor.isCurrentMonth()) {//非當前月
                    cellView.setTextColor(0xFF9C9C9C);
                    //這裡要修改為cellView.setEnable();
                }

                //設置日期
                cellView.setText(cellDescriptor.getValue());

                //設置tag, 讓點擊的時候可以獲取到值
                cellView.setTag(cellDescriptor);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();//不能去掉,不然會出問題
        mGridView = (CalendarGridView) findViewById(R.id.calendar_gridview);
    }


    private void setDayTextColor(int dayTextColorResId) {
        mGridView.setDayTextColor(dayTextColorResId);
    }

}
