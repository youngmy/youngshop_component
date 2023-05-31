package com.young.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {

    @SuppressLint("SimpleDateFormat")
    fun getDate(pattern: String = "yyyy-MM-dd", amount: Int =0): String {
        val simpleDateFormat = SimpleDateFormat(pattern)
        val calendar = GregorianCalendar()
        calendar.time = Date()
        //获取昨天、今天、明天的日期 -1 , 0 ,1
        calendar.add(Calendar.DATE, amount)
        return simpleDateFormat.format(calendar.time)
    }

}