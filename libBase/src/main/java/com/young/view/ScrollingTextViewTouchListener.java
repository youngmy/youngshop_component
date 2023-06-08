package com.young.view;



import android.view.MotionEvent;
import android.view.View;

/**
 * 滑动的TextView的触摸监听
 */
public class ScrollingTextViewTouchListener implements View.OnTouchListener {

    private float mLastTouchY;

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        //当前view是否可上滑
        boolean canUpScroll = v.canScrollVertically(-1);
        //当前view是否可下滑
        boolean canDownScroll = v.canScrollVertically(1);
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mLastTouchY = e.getY();
            //父节点不拦截子节点
            v.getParent().requestDisallowInterceptTouchEvent(true);
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            // 获取手指当前所在的位置
            final float y = e.getY();
            // 计算距离
            float dy = mLastTouchY - y;
//            LogUtils.e("滑动textview", "canUpScroll=" + canUpScroll + "\ncanDownScroll=" + canDownScroll + "\ndy=" + dy);
            if (dy > 0) {
                //手指上滑
                v.getParent().requestDisallowInterceptTouchEvent(canDownScroll);
            } else {
                //手指下滑
                v.getParent().requestDisallowInterceptTouchEvent(canUpScroll);
            }
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            //父节点拦截子节点
            v.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

}