package com.young.view.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;

/**
 * 仅一个文件的展开收缩LinearLayout 有显示问题
 * https://juejin.cn/post/7130639529123250213
 */
public class JavaExpandableLinearLayout extends LinearLayout implements View.OnClickListener {

    private TextView txtViewTip;
    /**
     * 是否是展开状态，默认是隐藏
     */
    private boolean isExpand = false;
    private boolean boolHasBottom = false;

    private int intDefaultItemCount = 2;
    /**
     * 待展开显示的文字
     */
    private String strExpandText = "显示更多";
    /**
     * 待隐藏显示的文字
     */
    private String strHideText = "收起内容";
    private float fontTextSize=13;
    private int intTextColor= android.R.color.black;

    public void outUseMethodChangeDefaultItemCount(int intDefaultItemCount) {
        this.intDefaultItemCount = intDefaultItemCount;
    }

    public void outUseMethodChangeExpandText(String strExpandText) {
        this.strExpandText = strExpandText;
    }

    public void outUseMethodChangeHideText(String strHideText) {
        this.strHideText = strHideText;
    }
    public void outUseMethodChangeExpandHideTextSize(float fontTextSize) {
        this.fontTextSize = fontTextSize;
    }
    public void outUseMethodChangeExpandHideTextColor(@ColorInt int intTextColor) {
        this.intTextColor = intTextColor;
    }
    public JavaExpandableLinearLayout(Context context) {
        this(context, null);
    }

    public JavaExpandableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JavaExpandableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置垂直方向
        setOrientation(VERTICAL);
    }

    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException("ExpandableLinearLayout只支持垂直布局");
        }
        super.setOrientation(orientation);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        justToAddBottom(childCount);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 判断是否要添加底部
     */
    private void justToAddBottom(int childCount) {
        if (childCount > intDefaultItemCount && !boolHasBottom) {
            boolHasBottom = true;
            //要使用默认底部,并且还没有底部
            LinearLayout linearLayoutBottom = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParamsBottom = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayoutBottom.setLayoutParams(layoutParamsBottom);
            linearLayoutBottom.setGravity(Gravity.CENTER);
            txtViewTip = new TextView(getContext());
            txtViewTip.setText("展开更多");
            txtViewTip.setTextSize(fontTextSize);
            txtViewTip.setTextColor(intTextColor);
            LinearLayout.LayoutParams layoutParamsBottomTxt = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            txtViewTip.setLayoutParams(layoutParamsBottomTxt);
            //设置个边距
            layoutParamsBottomTxt.setMargins(0, 10, 0, 10);
            linearLayoutBottom.addView(txtViewTip);
            linearLayoutBottom.setOnClickListener(this);
            //添加底部
            addView(linearLayoutBottom);
            hide();
//            Log.e("TAG", "justToAddBottom: zou l zhe ");
        }
    }

    /**
     * 刷新UI
     */
    private void refreshView(View view) {
        int childCount = getChildCount();
        if (childCount > intDefaultItemCount) {
            if (childCount - intDefaultItemCount == 1) {
                //刚超过默认，判断是否要添加底部
                justToAddBottom(childCount);
            }
            //大于默认数目的先隐藏
            view.setVisibility(GONE);
        }
    }

    /**
     * 展开
     */
    private void expand() {
        for (int i = intDefaultItemCount; i < getChildCount(); i++) {
            //从默认显示条目位置以下的都显示出来
            View view = getChildAt(i);
            view.setVisibility(VISIBLE);
        }
    }

    /**
     * 收起
     */
    private void hide() {
        int endIndex = getChildCount() - 1;
        for (int i = intDefaultItemCount; i < endIndex; i++) {
            //从默认显示条目位置以下的都隐藏
            View view = getChildAt(i);
            view.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        outUseMethodToggle();
    }

    /**
     * 外部也可调用 展开或关闭
     */
    public void outUseMethodToggle() {
        if (isExpand) {
            hide();
            txtViewTip.setText(strExpandText);
        } else {
            expand();
            txtViewTip.setText(strHideText);
        }
        isExpand = !isExpand;
    }

    /**
     * 外部可随时添加子view
     */
    public void outUseMethodAddItem(View view) {
        int childCount = getChildCount();
        //插在底部之前
        addView(view, childCount - 1);
        refreshView(view);
    }
}
