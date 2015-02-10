package com.zj.example.calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.zj.example.calendar.view.CalendarView;
import com.zj.example.calendar.R;

import org.joda.time.LocalDate;

/**
 *
 * create by zhengjiong
 * Date: 2015-01-18
 * Time: 19:11
 */
public class ViewPagerDemoActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_demo_layout);


        //CalendarPickerView calendarPickerView = (CalendarPickerView) findViewById(R.id.viewpager);

        //calendarPickerView.init(getSupportFragmentManager(), LocalDate.now(), new LocalDate(2015, 1, 1), new LocalDate(2015, 4, 30));

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.init(getSupportFragmentManager(), LocalDate.now(), new LocalDate(2014, 1, 1), new LocalDate(2015, 12, 30));

    }

}
