package com.young.module.gift.utils

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.helper.YLogHelper
import com.young.commonconfig.helper.*
import com.young.widget.bean.CommonClickBean

object GiftCommonClickHelper {

    private val simpleName=GiftCommonClickHelper::class.java.simpleName

    fun commonClick(context: Context?, bean: CommonClickBean) {
        val getTitle = bean.title
        val getClickType = bean.clickType
        YLogHelper.i("$simpleName===", "===getTitle===$getTitle===getClickType===$getClickType")
//        TestJavaActivity.startActivity(context)
//        TestKotlinActivity.startActivity(context)
        when (getClickType) {
            "good_music"-> {
                ARouter.getInstance().build(goodMusicActivity).navigation()
            }
            "demo" -> {
                ARouter.getInstance().build(demoActivity).navigation()
            }
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