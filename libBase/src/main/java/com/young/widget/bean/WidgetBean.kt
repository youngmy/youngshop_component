package com.young.widget.bean

import java.io.Serializable

data class WidgetBean(
    var code:Int=0,
    var msg:String="请求失败，请稍后重试！",
    var data: List<WidgetDataBean> = arrayListOf()
):Serializable
