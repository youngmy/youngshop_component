package com.young.widget.utils

import android.content.Context
import com.helper.YLogHelper
import com.young.widget.bean.CommonClickBean

object CommonClickHelper {

    fun commonClick(context: Context?, bean: CommonClickBean) {
        val getTitle = bean.title
        val getClickType = bean.clickType
        YLogHelper.i("CommonClickHelper===", "===getTitle===$getTitle")
//        TestJavaActivity.startActivity(context)
//        TestKotlinActivity.startActivity(context)
        when (getClickType) {
            "verification_code" -> {
//                VerificationCodeActivity.startActivity(context)
            }
            "test_kotlin" -> {
//                TestKotlinActivity.startActivity(context)
            }
            else -> {

            }
        }
    }

}