package com.young.utils

import android.text.TextUtils
import java.text.NumberFormat

fun String.getDouble(normalValue:Double=0.0): Double {
    return if (TextUtils.isEmpty(this)) {
        normalValue
    } else {
        this.toDouble()
    }
}

fun String.getInt(normalValue:Int=0): Int {
    return if (TextUtils.isEmpty(this)) {
        normalValue
    } else {
        this.toInt()
    }
}

fun Double.reservedDecimal(
    maximumFractionDigits: Int = 2,
    minimumFractionDigits: Int = 2,
    isGroupingUsed: Boolean = false
): String {
    val numberFormat = NumberFormat.getNumberInstance()
    numberFormat.maximumFractionDigits = maximumFractionDigits
    numberFormat.minimumFractionDigits = minimumFractionDigits
    numberFormat.isGroupingUsed = isGroupingUsed
    return numberFormat.format(this)
}