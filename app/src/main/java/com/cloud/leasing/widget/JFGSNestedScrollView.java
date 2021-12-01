package com.cloud.leasing.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class JFGSNestedScrollView extends NestedScrollView {

    private OnJFGSScrollChanged onJFGSScrollChanged;

    public JFGSNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public JFGSNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JFGSNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onJFGSScrollChanged != null) {
            onJFGSScrollChanged.onScroll(l, t, oldl, oldt);
        }
    }

    public void setOnJFGSScrollChanged(OnJFGSScrollChanged onJFGSScrollChanged) {
        this.onJFGSScrollChanged = onJFGSScrollChanged;
    }

    public interface OnJFGSScrollChanged {
        /**
         * 滑动的方法
         *
         * @param left    左边
         * @param top     上边
         * @param oldLeft 之前的左边
         * @param oldTop  之前的上边
         */
        void onScroll(int left, int top, int oldLeft, int oldTop);
    }

}
