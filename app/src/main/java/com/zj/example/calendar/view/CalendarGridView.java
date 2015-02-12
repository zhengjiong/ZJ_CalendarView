package com.zj.example.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by zhengjiong on 15/1/16.
 */
public class CalendarGridView extends ViewGroup{

    private VelocityTracker mTracker;
    private Scroller mScroller;


    private int mTouchSlop;
    private int mMininumFlingVelocity;
    private int mMaximumFlingVelocity;


    public CalendarGridView(Context context) {
        super(context);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTracker = VelocityTracker.obtain();
        mScroller = new Scroller(context);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);

        //手的移动要大于这个距离才开始移动控件,获得能够进行手势滑动的距离
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

        //获得允许执行一个滑动手势动作的最小速度值
        mMininumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();

        //获得允许执行一个滑动手势动作的最大速度值
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        for (int i = 0, childNum = getChildCount(); i < childNum; i++) {
            View childView = getChildAt(i);

            childView.layout(l, top, r, top + childView.getMeasuredHeight());

            top += childView.getMeasuredHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalHeight = 0;
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        //這裡heightMeasureSize得出的高度是整個屏幕的大小,所以不能夠使用,需要計算出實際高度
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        //int cellSize = widthMeasureSize / 7;

        for (int i = 0, childNum = getChildCount(); i < childNum; i++) {
            View childView = getChildAt(i);

            //int childWidthMeasureSpec;
            //int childHeightMeasureSpec;

            //childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            totalHeight += childView.getMeasuredHeight();
        }

        /**
         * 這裡必須使用totalHeight,不然高度有問題
         */
        setMeasuredDimension(widthMeasureSize, totalHeight);
    }

    public void setDayTextColor(int dayTextColorResId) {
        for (int i = 0, childNum = getChildCount(); i < childNum; i++) {
            CalendarRowView rowView = (CalendarRowView) getChildAt(i);

            ColorStateList colors = getResources().getColorStateList(dayTextColorResId);

            rowView.setCellTextColor(colors);
        }
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        Log.i("zj", "gridview dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
