package com.young.widget.bean

import java.io.Serializable

data class WidgetDataBean(
    var viewType:String="",
    var title:String="",
    var led:String="",
    var content: List<CommonClickBean> = arrayListOf(),
    var lineNumber:Int=1
): Serializable
