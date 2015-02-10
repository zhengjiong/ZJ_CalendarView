package com.zj.example.calendar.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.google.common.collect.Lists;
import com.zj.example.calendar.bean.MonthCellDescriptor;
import com.zj.example.calendar.bean.MonthDescriptor;
import com.zj.example.calendar.MonthViewFragment;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 *
 * Created by zhengjiong on 2015/2/2.
 */
public class CalendarPickerView extends ViewPager{
    private final DateTimeFormatter yyyyMM = DateTimeFormat.forPattern("yyyy-MM");
    private final DateTimeFormatter d = DateTimeFormat.forPattern("d");
    private final LocalDate mToday = LocalDate.now();

    private Context mContext;

    private List<MonthDescriptor> mMonths = Lists.newArrayList();
    private List<List<List<MonthCellDescriptor>>> mCells = Lists.newArrayList();

    private MonthPagerAdapter mMonthPagerAdapter;

    private int mSelectedPosition;

    private MonthCellDescriptor mCurrentSelectedCell;//當前被選擇的cell

    private OnCellClickListener mOnCellClickListener = new OnCellClickListener();

    public CalendarPickerView(Context context) {
        this(context, null);
    }

    public CalendarPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     * 處理Cell點擊事件, 改變背景顏色和字體顏色
     */
    private class OnCellClickListener implements CalendarMonthView.DayClickListener{

        @Override
        public void handleClick(MonthCellDescriptor cell) {
            if (cell.isCurrentMonth()) {
                //設置之前的Cell為未選擇狀態
                mCurrentSelectedCell.setSelected(false);

                //設置當前被點擊的Cell為已選擇狀態
                mCurrentSelectedCell = cell;
                mCurrentSelectedCell.setSelected(true);
                //刷新
                mMonthPagerAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     *
     * @param fragmentManager
     * @param selectedDay 當前選擇的日期
     * @param minDate 最小可以顯示的日期
     * @param maxDate 最大可以顯示的日期
     */
    public void init(FragmentManager fragmentManager, LocalDate selectedDay, LocalDate minDate, LocalDate maxDate) {
        //設置ViewPager的Adapter
        mMonthPagerAdapter = new MonthPagerAdapter(fragmentManager);
        setAdapter(mMonthPagerAdapter);


        LocalDate countDate = new LocalDate(minDate.getYear(), minDate.getMonthOfYear(), 1);

        //最小時間和最大時間相差的月數
        int monthsCount = Months.monthsBetween(minDate, maxDate).getMonths();

        /**
         * 生成每天的數據
         */
        for (int i = 0; i <= monthsCount; i++) {
            //Log.i("zj", "maxDate= " + countDate.getMonthOfYear());
            MonthDescriptor monthDescriptor = new MonthDescriptor(countDate.getMonthOfYear(), countDate.getYear(), countDate.toString(yyyyMM));

            mMonths.add(monthDescriptor);
            mCells.add(getMonthCell(countDate.getYear(), countDate.getMonthOfYear()));

            //獲取viewpager需要切換到哪個position
            if (selectedDay.getYear() == countDate.getYear()
                    && selectedDay.getMonthOfYear() == countDate.getMonthOfYear()){
                mSelectedPosition = i;//當前要顯示的月的位置
            }

            countDate = countDate.plusMonths(1);
        }

        mMonthPagerAdapter.notifyDataSetChanged();


        if (mSelectedPosition != 0){
            scrollToSelected();
        }
    }

    /**
     * 獲取給定年月的數據
     * @param year
     * @param month
     * @return
     */
    private List<List<MonthCellDescriptor>> getMonthCell(int year, int month) {
        //指定年月的第一天
        LocalDate monthLocalDate = new LocalDate(year, month, 1);

        List<List<MonthCellDescriptor>> monthDescriLists = Lists.newArrayList();

        if (monthLocalDate.getDayOfWeek() != DateTimeConstants.SUNDAY) {
            //monthLocalDate = monthLocalDate.withDayOfMonth(1);
            //如果本月的第一天不是星期天,則從上周星期天開始顯示
            monthLocalDate = monthLocalDate.minusWeeks(1).withDayOfWeek(7);
        }
        //monthLocalDate = monthLocalDate.withDayOfMonth(1)//獲取當月第一天

        for (int i = 0; i < 6; i++) {//一月顯示六周的數據
            List<MonthCellDescriptor> weekDescriptors = Lists.newArrayList();
            for (int j = 0; j < 7; j++) {//一星期7天
                MonthCellDescriptor cell = new MonthCellDescriptor(
                        new LocalDate(monthLocalDate.getYear(), monthLocalDate.getMonthOfYear(), monthLocalDate.getDayOfMonth()),
                        monthLocalDate.toString(d),
                        monthLocalDate.getMonthOfYear() == month,
                        false,//isSelected
                        isToday(monthLocalDate),
                        true//isSelectable
                );
                weekDescriptors.add(cell);

                if (cell.isToday()) {
                    //初始化的時候使用當天時間為选中的日期
                    cell.setSelected(true);
                    mCurrentSelectedCell = cell;

                }

                monthLocalDate = monthLocalDate.plusDays(1);
            }
            monthDescriLists.add(weekDescriptors);
        }

        return monthDescriLists;
    }

    /**
     * 判斷是否是今天
     * @return
     */
    private boolean isToday(LocalDate cellLocalDate) {
        if (mToday.getYear() == cellLocalDate.getYear() &&
                mToday.getMonthOfYear() == cellLocalDate.getMonthOfYear() &&
                mToday.getDayOfMonth() == cellLocalDate.getDayOfMonth()) {
            return true;
        }
        return false;
    }


    class MonthPagerAdapter extends FragmentPagerAdapter{

        public MonthPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return MonthViewFragment.getInstance(mOnCellClickListener, mMonths.get(i), mCells.get(i));
        }

        @Override
        public int getCount() {
            return mMonths.size();
        }

        /**
         * 加上這個notifyDataChange才能刷新數據
         */
        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    private void scrollToSelected(){
        setCurrentItem(mSelectedPosition, true);
    }

    /*@Override
    public void onPageSelected(int i) {
        MonthDescriptor monthDescriptor = mMonths.get(i);
        Toast.makeText(mContext, monthDescriptor.getLable(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int i, float v, int i1){
        super.onPageScrolled(i, v, i1);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}*/
}
