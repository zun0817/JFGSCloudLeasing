package com.cloud.dialoglibrary;

import android.content.Context;
import android.content.res.Resources;

public class UiUtils {

    private UiUtils() {
    }

    public static UiUtils getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * dip转px
     */
    public static int dip2px(Context context, float dip) {
        float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }

    /**
     * px转dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 根据id获取尺寸
     */
    public static int getDimens(Context context, int id) {
        return getResources(context).getDimensionPixelSize(id);
    }


    private static final class Holder {
        private static final UiUtils INSTANCE = new UiUtils();
    }

}
