package com.zj.example.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zj.example.calendar.view.CalendarMonthView;
import com.zj.example.calendar.bean.MonthCellDescriptor;
import com.zj.example.calendar.bean.MonthDescriptor;

import java.util.List;

/**
 * create by zhengjiong
 * Date: 2015-01-18
 * Time: 19:31
 */
public class MonthViewFragment extends Fragment{

    private CalendarMonthView.DayClickListener mListener;
    private MonthDescriptor mMonth;
    private List<List<MonthCellDescriptor>> mCells;

    private CalendarMonthView mMonthView;

    public static MonthViewFragment getInstance(CalendarMonthView.DayClickListener listener ,MonthDescriptor month, List<List<MonthCellDescriptor>> cells) {

        MonthViewFragment fragment = new MonthViewFragment();
        fragment.mListener = listener;
        fragment.mMonth = month;1
        fragment.mCells = cells;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**
         * 這裡也可以使用CalendarMonthView.create方法創建,
         * 後面來比較兩種方法的使用區別
         */
        mMonthView = (CalendarMonthView) inflater.inflate(R.layout.month, null);
        return mMonthView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMonthView.init(mListener, mMonth, mCells);
    }
}
