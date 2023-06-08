package com.young.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import com.young.application.BaseApplication

object MyStatusBarUtil {

    /**
     * 获取状态栏高度，单位px
     */
    @SuppressLint("InternalInsetResource")
    fun getHeight(): Int {
        var result = 0
        val resourceId: Int = BaseApplication.context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = BaseApplication.context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 设置状态栏文字
     */
    fun setBarTextModal(activity: Activity, dark: Boolean) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

}