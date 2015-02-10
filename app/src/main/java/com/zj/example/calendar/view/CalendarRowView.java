package com.zj.example.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zj.example.calendar.bean.MonthCellDescriptor;

/**
 * create by zhengjiong
 * Date: 2015-01-15
 * Time: 21:59
 */
public class CalendarRowView extends ViewGroup implements View.OnClickListener{

    private CalendarMonthView.DayClickListener listener;

    public CalendarRowView(Context context) {
        super(context);
    }

    public CalendarRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 設置子view的Onclick事件
     */
    @Override
    public void addView(View child, int index, LayoutParams params) {
        child.setOnClickListener(this);
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int parentWidthSpec, int parentHeightSpec) {
        long start = System.currentTimeMillis();

        int parentWidth = MeasureSpec.getSize(parentWidthSpec);
        //這裡parentHeight得出的高度是整個屏幕的大小,所以不能夠使用,需要計算出實際高度
        int parentHeight = MeasureSpec.getSize(parentHeightSpec);
        int totalHeight = 0;
        int childWidth = parentWidth / 7;

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        //讓高寬一致
        int childHeightMeasureSpec = childWidthMeasureSpec;

        //設置子控件的大小
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {

            View childView = getChildAt(i);

            /**
             * View child, int parentWidthMeasureSpec,
             * int parentHeightMeasureSpec
             * 這樣是使用父控件的spec去計算子控件的高宽,但是这样会显示不出来
             * 這裡還需要多理解,查看方法源碼理解
             */
            //measureChild(childView, parentWidthSpec, parentHeightSpec);

            //這樣做可以填寫我們自己的spec
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            /**
             * 計算父View的高度
             * 使用最高的一個子view的高度來作為父view的高度
             */
            if (totalHeight < childView.getMeasuredHeight()) {
                totalHeight = childView.getMeasuredHeight();
            }
        }

        setMeasuredDimension(parentWidth, totalHeight);

        //Log.i("zj", "CalendarRowView onMeasure " + (System.currentTimeMillis() - start));
    }

    /**
     * layout子控件
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int bottom = b - t;

        for (int i = 0, childNum = getChildCount(); i < childNum; i++) {
            View childView = getChildAt(i);

            int left = i * childView.getMeasuredWidth();
            int right = (i + 1) * childView.getMeasuredWidth();

            childView.layout(left, top, right, bottom);
        }
    }

    public void setCellTextColor(ColorStateList colors) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView cellView = (TextView) getChildAt(i);
            cellView.setTextColor(colors);
        }
    }

    public void setCellTextColor(int resId) {
        for (int i = 0; i < getChildCount(); i++) {
            TextView cellView = (TextView) getChildAt(i);
            cellView.setTextColor(resId);
        }
    }

    public void setCellBackground(int resId) {
        for (int i = 0; i < getChildCount(); i++) {
            CalendarCellView cellView = (CalendarCellView) getChildAt(i);
            cellView.setBackgroundResource(resId);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            /**
             * 處理點擊事件并讓viewpager囘調刷新
             *
             * 之所以要這樣處理是爲了方便CalendarPickerView刷新,
             * 不然需要把adapter傳遞到這裡才可以刷新
             */
            listener.handleClick((MonthCellDescriptor) v.getTag());
        }
    }

    public void setListener(CalendarMonthView.DayClickListener listener) {
        this.listener = listener;
    }

}
