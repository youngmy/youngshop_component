package com.young.view.linearlayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt


/**
 * 仅一个文件的展开收缩LinearLayout  有显示问题
 * https://juejin.cn/post/7130639529123250213
 */
class KotlinExpandableLinearLayout : LinearLayout, View.OnClickListener {


    var txtViewTip: TextView? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        //设置垂直方向
        orientation = VERTICAL
    }

    /**
     * 是否是展开状态，默认是隐藏
     */
    var isExpand = false

    var intDefaultItemCount = 2
    var boolHasBottom = false

    /**
     * 待展开显示的文字
     */
    var strExpandText = "显示更多"

    /**
     * 待隐藏显示的文字
     */
    var strHideText = "收起内容"
    var fontTextSize = 0f
    var intTextColor = android.R.color.black

    fun outUseMethodChangeDefaultItemCount(intDefaultItemCount: Int) {
        this.intDefaultItemCount = intDefaultItemCount
    }

    fun outUseMethodChangeExpandText(strExpandText: String) {
        this.strExpandText = strExpandText
    }

    fun outUseMethodChangeHideText(strHideText: String) {
        this.strHideText = strHideText
    }

    fun outUseMethodChangeExpandHideTextSize(fontTextSize: Float) {
        this.fontTextSize = fontTextSize
    }

    fun outUseMethodChangeExpandHideTextColor(@ColorInt intTextColor: Int) {
        this.intTextColor = intTextColor
    }

    override fun setOrientation(orientation: Int) {
        require(HORIZONTAL != orientation) { "ExpandableLinearLayout只支持垂直布局" }
        super.setOrientation(orientation)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val childCount = childCount
        justToAddBottom(childCount)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    /**
     * 判断是否要添加底部
     */
    fun justToAddBottom(childCount: Int) {
        if (childCount > intDefaultItemCount && !boolHasBottom) {
            boolHasBottom = true
            //要使用默认底部,并且还没有底部
            val linearLayoutBottom = LinearLayout(context)
            val layoutParamsBottom =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            linearLayoutBottom.layoutParams = layoutParamsBottom
            linearLayoutBottom.gravity = Gravity.CENTER
            txtViewTip = TextView(context)
            txtViewTip?.text = "展开更多"
            txtViewTip?.textSize = fontTextSize
            txtViewTip?.setTextColor(intTextColor)
            val layoutParamsBottomTxt =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            txtViewTip?.layoutParams = layoutParamsBottomTxt
            //设置个边距
            layoutParamsBottomTxt.setMargins(0, 10, 0, 10)
            linearLayoutBottom.addView(txtViewTip)
            linearLayoutBottom.setOnClickListener(this)
            //添加底部
            addView(linearLayoutBottom)
            hide()
        }
    }

    /**
     * 刷新UI
     */
    fun refreshView(view: View) {
        val childCount = childCount
        if (childCount > intDefaultItemCount) {
            if (childCount - intDefaultItemCount == 1) {
                //刚超过默认，判断是否要添加底部
                justToAddBottom(childCount)
            }
            //大于默认数目的先隐藏
            view.visibility = GONE
        }
    }

    /**
     * 展开
     */
    fun expand() {
        for (i in intDefaultItemCount until childCount) {
            //从默认显示条目位置以下的都显示出来
            val view: View = getChildAt(i)
            view.visibility = VISIBLE
        }
    }

    /**
     * 收起
     */
    fun hide() {
        val endIndex = childCount - 1
        for (i in intDefaultItemCount until endIndex) {
            //从默认显示条目位置以下的都隐藏
            val view: View = getChildAt(i)
            view.visibility = GONE
        }
    }

    override fun onClick(v: View?) {
        outUseMethodToggle()
    }

    /**
     * 外部也可调用 展开或关闭
     */
    fun outUseMethodToggle() {
        if (isExpand) {
            hide()
            txtViewTip?.text = strExpandText
        } else {
            expand()
            txtViewTip?.text = strHideText
        }
        isExpand = !isExpand
    }

    /**
     * 外部可随时添加子view
     */
    fun outUseMethodAddItem(view: View) {
        val childCount = childCount
        //插在底部之前
        addView(view, childCount - 1)
        refreshView(view)
    }

}
