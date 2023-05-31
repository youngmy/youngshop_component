package com.helper

import android.content.Context
import android.widget.Toast

object YToastHelper {

    fun shortToast(context: Context?,text:CharSequence){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    fun longToast(context: Context?,text:CharSequence){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show()
    }

}