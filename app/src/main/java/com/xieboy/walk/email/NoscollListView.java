package com.xieboy.walk.email;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Walk on 2017/4/27.
 */
public class NoscollListView extends ListView {
    public NoscollListView(Context context) {
        super(context);
    }

    public NoscollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoscollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}