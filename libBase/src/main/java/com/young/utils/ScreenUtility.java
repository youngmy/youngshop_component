package com.young.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.young.application.BaseApplication;


public class ScreenUtility {
    private Context context;
    private static ScreenUtility instance;
    private DisplayMetrics dm;

    public static ScreenUtility getInstance() {
        if (instance == null) {
            instance = new ScreenUtility(BaseApplication.Companion.getContext());
        }
        return instance;
    }

    private ScreenUtility(Context context) {
        this.context = context;
        dm = this.context.getResources().getDisplayMetrics();
    }

    public final float getScale() {
        float scale = dm.density;
        return scale;
    }

    /**
     * px to dip
     *
     * @param pxValue
     * @return
     */
    public final int px2dip(float pxValue) {
        final float scale = dm.density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip to px
     *
     * @param dipValue
     * @return
     */
    public final int dip2px(float dipValue) {
        final float scale = dm.density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * acquire screen width.
     *
     * @return
     */
    public final float getScreenWidthDip() {
        return px2dip(dm.widthPixels);
    }

    /**
     * acquire screen width.
     *
     * @return
     */
    public final int getScreenWidth() {
        final int screenWeightPx = dm.widthPixels;
        return screenWeightPx;
    }

    /**
     * acquire screen width.
     *
     * @return
     */
    public final float getScreenHeightDip() {
        return px2dip(dm.heightPixels);
    }

    public final int getScreenHeight() {
        final int screenHeightPx = dm.heightPixels;
        return screenHeightPx;
    }

    //通知栏高度
    public final int getStatusBarHeight(){
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * view Revise
     *
     * @param v
     */
    public final void viewScreenRevise(View v) {
        if (v == null) return;
        float scale = getScreenWidthDip() / 320;
        if (scale == 1)
            return;

        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams newParams = (LinearLayout.LayoutParams) params;
            newParams.leftMargin = (int) (scale * newParams.leftMargin);
            newParams.rightMargin = (int) (scale * newParams.rightMargin);
            newParams.topMargin = (int) (scale * newParams.topMargin);
            newParams.bottomMargin = (int) (scale * newParams.bottomMargin);
            newParams.width = (int) (scale * newParams.width);
            newParams.height = (int) (scale * newParams.height);
            v.setLayoutParams(newParams);
        } else if (params instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams newParams = (RelativeLayout.LayoutParams) params;
            newParams.leftMargin = (int) (scale * newParams.leftMargin);
            newParams.rightMargin = (int) (scale * newParams.rightMargin);
            newParams.topMargin = (int) (scale * newParams.topMargin);
            newParams.bottomMargin = (int) (scale * newParams.bottomMargin);
            newParams.width = (int) (scale * newParams.width);
            newParams.height = (int) (scale * newParams.height);
            v.setLayoutParams(newParams);
        } else if (params instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams newParams = (FrameLayout.LayoutParams) params;
            newParams.leftMargin = (int) (scale * newParams.leftMargin);
            newParams.rightMargin = (int) (scale * newParams.rightMargin);
            newParams.topMargin = (int) (scale * newParams.topMargin);
            newParams.bottomMargin = (int) (scale * newParams.bottomMargin);
            newParams.width = (int) (scale * newParams.width);
            newParams.height = (int) (scale * newParams.height);
            v.setLayoutParams(newParams);
        } else {
            params.width = (int) (scale * params.width);
            params.height = (int) (scale * params.height);
            v.setLayoutParams(params);
        }

    }

    public int toDip(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }
}
