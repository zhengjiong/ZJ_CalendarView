package com.zj.example.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.zj.example.calendar.R;

/**
 *
 * create by zhengjiong
 * Date: 2015-01-15
 * Time: 22:00
 */
public class CalendarCellView extends TextView{

    private boolean isToday = false;
    private boolean isCurrent = false;
    private boolean isSelectable = false;

    private final int[] STATE_TODAY = {
        R.attr.state_today
    };

    private final int[] STATE_CURRENT = {
        R.attr.state_current
    };

    private final int[] STATE_CLICKABLE = {
        R.attr.state_selectable
    };

    public CalendarCellView(Context context) {
        this(context, null);
    }

    public CalendarCellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setToday(boolean isToday) {
        this.isToday = isToday;
        refreshDrawableState();
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
        refreshDrawableState();
    }

    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 3);

        if (isToday) {
            mergeDrawableStates(drawableState, STATE_TODAY);
        }
        if (isSelectable) {
            mergeDrawableStates(drawableState, STATE_CLICKABLE);
        }
        if (isCurrent) {
            mergeDrawableStates(drawableState, STATE_CURRENT);
        }

        return drawableState;
    }

}
