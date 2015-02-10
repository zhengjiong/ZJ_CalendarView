package com.zj.example.calendar.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zj.example.calendar.R;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by zhengjiong on 2015/2/3.
 */
public class CalendarView extends LinearLayout{
    private Context mContext;
    private CalendarRowView mHeaderView;
    private CalendarPickerView mPickerView;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.calendar_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHeaderView = (CalendarRowView) findViewById(R.id.week_header);
        mPickerView = (CalendarPickerView) findViewById(R.id.viewpager);

        populateHeader();
    }

    /**
     * 生成頂部header
     */
    public void populateHeader(){
        LocalDate today = LocalDate.now();
        //日曆從星期天開始顯示
        LocalDate firstDay = today.withDayOfWeek(DateTimeConstants.SUNDAY);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("E");

        for (int i = 0, childNum = mHeaderView.getChildCount(); i < childNum; i++) {

            CalendarCellView headerView = (CalendarCellView) mHeaderView.getChildAt(i);

            headerView.setText(firstDay.toString(formatter));

            firstDay = firstDay.plusDays(1);
        }
    }

    public void init(FragmentManager fragmentManager, LocalDate selectedDay, LocalDate minDate, LocalDate maxDate){
        /**
         * 初始化Viewpager
         */
        mPickerView.init(fragmentManager, selectedDay, minDate, maxDate);
    }
}
