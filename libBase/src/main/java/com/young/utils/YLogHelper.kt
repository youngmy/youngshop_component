package com.helper

import android.util.Log

object YLogHelper {

    fun i(tag:String="YLogHelper",msg:String="===msg==="){
        Log.i("$tag===",msg)
    }

    fun e(tag:String="YLogHelper",msg:String="===msg==="){
        Log.i("$tag===",msg)
    }

}