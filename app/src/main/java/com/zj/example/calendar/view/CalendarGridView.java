package com.zj.example.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhengjiong on 15/1/16.
 */
public class CalendarGridView extends ViewGroup{


    public CalendarGridView(Context context) {
        super(context);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        //result = true;
        Log.i("zj", "GridView onInterceptTouchEvent result = " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
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
