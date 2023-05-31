package com.young.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.young.module.libbase.R

@SuppressLint("CustomViewStyleable")
class MyRulerView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var call:((String)->Unit)? = null


    private var mLinePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)    //刻度画笔
    private var mTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)    //文字画笔

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    //标尺
    private var mMaxValue = 250f           //最大值
    private var mMinValue = 80f            //最小值
    private var mPerValue = 1f             //最小刻度值，最小单位
    private var mLineSpace = 5f            //两条刻度之间的间隔距离

    private var mTotalLine = 0             //计算mMaxValue-mMinValue之间一共有多少条刻度线

    private var mMaxOffset = 0             //所有刻度共有多长 （mTotalLine-1）* mLineSpaceWidth

    private var mOffset = 0f               // 默认状态下，mSelectorValue所在的位置  位于尺子总刻度的位置

    private var mLastX: Int = 0
    private var mMove: Int = 0


    //刻度线
    private var mLineMaxLength = 40f       //三种不同长度(如刻度80cm-250cm)，最长的那根线（80,90,100,...）时的线高度
    private var mLineMidLength = 30f       //中等长度（85,95,105,...）时的线高度
    private var mLineMinLength = 20f       //最短长度（81,82,83,...）时的线高度
    private var mLineWidth = 1f            //刻度线的粗细

    private var mLineColor =Color.parseColor("#801D2129") //context.getColor(R.color.white6)    //刻度线颜色

    private var mSelectorValue = 100.0f                          // 未选择时 默认的值 指针指向的默认值


    //标尺下方文字
    private var mTextColor = Color.parseColor("#000000")//context.getColor(R.color.base_color_0)       //文字颜色
    private var mTextSize = 35f                                    //文字大小
    private var mTextMarginTop = 10f                               //文字与上方的距离
    private var mTextHeight = 40f                                  //尺子刻度下方数字的高度

    private var mMinVelocity = 0


    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null

    init {
        this.mLineSpace = myFloat(mLineSpace)
        this.mLineWidth = myFloat(mLineWidth)
        this.mLineMidLength = myFloat(mLineMidLength)
        this.mLineMinLength = myFloat(mLineMinLength)
        this.mTextHeight = myFloat(mTextHeight)

        mScroller = Scroller(context)

        val styleable = context.obtainStyledAttributes(attrs, R.styleable.MyRulerView)

        mMaxValue = styleable.getFloat(R.styleable.MyRulerView_maxValue, mMaxValue)
        mMinValue = styleable.getFloat(R.styleable.MyRulerView_minValue, mMinValue)
        mPerValue = styleable.getFloat(R.styleable.MyRulerView_perValue, mPerValue)
        mLineSpace = styleable.getDimension(R.styleable.MyRulerView_lineSpaceWidth, mLineSpace)
        mSelectorValue = styleable.getFloat(R.styleable.MyRulerView_selectorValue, 0f)

        mLineMaxLength = styleable.getDimension(R.styleable.MyRulerView_lineMaxHeight, mLineMaxLength)
        mLineMidLength =  styleable.getDimension(R.styleable.MyRulerView_lineMidHeight, mLineMidLength)
        mLineMinLength = styleable.getDimension(R.styleable.MyRulerView_lineMinHeight, mLineMinLength)
        mLineWidth = styleable.getDimension(R.styleable.MyRulerView_lineWidth, mLineWidth)

        mLineColor = styleable.getColor(R.styleable.MyRulerView_lineColor, mLineColor)


        mTextColor = styleable.getColor(R.styleable.MyRulerView_textColor, mTextColor)
        mTextSize = styleable.getDimension(R.styleable.MyRulerView_textSize, mTextSize)
        mTextMarginTop = styleable.getDimension(R.styleable.MyRulerView_textMarginTop, mTextMarginTop)

        styleable.recycle()

        mMinVelocity = ViewConfiguration.get(getContext()).scaledMinimumFlingVelocity
        initPaint()
        setRulerValue(mSelectorValue, mMaxValue,mMinValue , mPerValue)

    }

    fun myFloat(paramFloat: Float) = 0.5f + paramFloat * 1.0f

    /**
     * 初始化刻度线画笔、标尺下方文字画笔
     */
    private fun initPaint() {
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize
        mTextPaint.typeface = Typeface.DEFAULT_BOLD

        mLinePaint.color = mLineColor
        mLinePaint.strokeWidth = mLineWidth
    }

    /**
     * 设置标尺的值
     */
    private fun setRulerValue(
        selectorValue: Float,
        maxValue: Float,
        minValue: Float,
        preValue: Float
    ) {
        Log.d("mSelectorValue---", mSelectorValue.toString())
        mSelectorValue = selectorValue
        mMaxValue = maxValue
        mMinValue = minValue
        mPerValue = preValue * 10f
        mTotalLine =
            ((mMaxValue * 10 - mMinValue * 10) / mPerValue).toInt() + 1  //需要画 mTotalLine 条刻度线
        mMaxOffset =
            (-(mTotalLine - 1) * mLineSpace).toInt()         //mTotalLine条刻度线之间有 mTotalLine-1 个间距
        mOffset = (mMinValue - mSelectorValue) / mPerValue * mLineSpace * 10
        invalidate()
        visibility = VISIBLE

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            mWidth = w
            mHeight = h
        }
    }

    /**
     * 绘制刻度线
     */
    override fun onDraw(canvas: Canvas?) {
        var left: Float
        var value: String
        var height: Float
        val srcPointX = mWidth / 2
        super.onDraw(canvas)
        for (i in 0 until mTotalLine) {
            left = srcPointX + mOffset + i * mLineSpace
            if (left < 0 || left > width) {
                continue
            }
            //整10时，更改绘制时线的高度
            if (i % 10 == 0) {
                height = mLineMaxLength
                value = (mMinValue + i * mPerValue / 10).toInt().toString()
                mLinePaint.color =Color.parseColor("#801D2129")// context.getColor(R.color.white6)
                //绘制刻度线下方数字
                canvas?.drawText(value, left - mTextPaint.measureText(value) / 2, height + mTextMarginTop + mTextHeight, mTextPaint)
                
            } else if (i % 5 == 0) {
                height = mLineMidLength
                mLinePaint.color =Color.parseColor("#261D2129")// context.getColor(R.color.white5)
            } else {
                height = mLineMinLength
                mLinePaint.color =Color.parseColor("#261D2129")// context.getColor(R.color.white5)
            }
            //画刻度线
            canvas?.drawLine(left, 0f, left, height, mLinePaint)

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var xPosition = event?.x?.toInt()
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }

        mVelocityTracker?.addMovement(event)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mScroller?.forceFinished(true)
                mLastX = xPosition!!
                mMove = 0
            }
            MotionEvent.ACTION_MOVE -> {
                mMove = mLastX - xPosition!!
                changeMoveAndValue()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                countMoveEnd()
                countVelocityTracker()
                return false

            }
        }
        mLastX = xPosition!!
        return true
    }

    /**
     * 滑动完成后如果指针落在两条刻度中间，则指向靠近的那条指针
     */
    private fun countMoveEnd() {
        mOffset -= mMove.toFloat()
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset.toFloat()
        } else if (mOffset >= 0) {
            mOffset = 0f
        }
        mLastX = 0
        mMove = 0
        mSelectorValue =
            mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mLineSpace) * mPerValue / 10.0f
        mOffset = (mMinValue - mSelectorValue) * 10.0f / mPerValue * mLineSpace

        call?.invoke(mSelectorValue.toInt().toString())
        postInvalidate()
    }

    private fun countVelocityTracker() {
        mVelocityTracker?.computeCurrentVelocity(1000) //初始化速率的单位
        val xVelocity = mVelocityTracker!!.getXVelocity() //当前的速度
        if (Math.abs(xVelocity) > mMinVelocity) {
            mScroller!!.fling(0, 0, xVelocity.toInt(), 0, Int.MIN_VALUE, Int.MAX_VALUE, 0, 0)
        }
    }


    /**
     * 滑动后的操作
     */
    private fun changeMoveAndValue() {
        mOffset -= mMove.toFloat()
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset.toFloat()
            mMove = 0
            mScroller!!.forceFinished(true)
        } else if (mOffset >= 0) {
            mOffset = 0f
            mMove = 0
            mScroller!!.forceFinished(true)
        }
        mSelectorValue =
            mMinValue + Math.round(Math.abs(mOffset) * 1.0f / mLineSpace) * mPerValue / 10.0f
        call?.invoke(mSelectorValue.toInt().toString())
        postInvalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.computeScrollOffset()) {       //mScroller.computeScrollOffset()返回 true表示滑动还没有结束
            if (mScroller!!.currX == mScroller!!.finalX) {
                countMoveEnd()
            } else {
                val xPosition = mScroller!!.currX
                mMove = mLastX - xPosition
                changeMoveAndValue()
                mLastX = xPosition
            }
        }
    }

    fun setTextChangedListener(call: (String) -> Unit) {
        this.call = call
    }
}