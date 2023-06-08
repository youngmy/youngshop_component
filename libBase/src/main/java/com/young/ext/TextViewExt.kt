package com.young.ext

import android.content.Context
import android.graphics.Paint

import android.widget.TextView
import androidx.annotation.ColorRes

fun TextView.setUnderLine(): TextView {
    paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
    return this
}

fun TextView.setTextViewColor(context: Context?, @ColorRes id: Int): TextView {
//    setTextColor(ColorHelper.getColor(context!!, id))
    return this
}