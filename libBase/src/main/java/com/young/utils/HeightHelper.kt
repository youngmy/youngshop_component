package com.young.utils

import android.view.View
import android.widget.LinearLayout

object HeightHelper {

    fun setHeightInLinearLayout(view: View?, width: Int, height: Int) {
        val layoutParams = LinearLayout.LayoutParams(view?.layoutParams)
        layoutParams.height = GlobalData.getInstance().screenData.screenWidth * height / width
        view?.layoutParams = layoutParams
    }

}