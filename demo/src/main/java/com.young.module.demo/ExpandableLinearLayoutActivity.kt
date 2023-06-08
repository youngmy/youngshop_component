package com.young.module.demo

import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.activity.BaseActivity
import com.young.commonconfig.helper.expandableLinearLayoutActivity
import com.young.module.demo.databinding.ActivityExpandableLinearLayoutBinding
import java.util.Timer
import java.util.TimerTask

@Route(path = expandableLinearLayoutActivity)
class ExpandableLinearLayoutActivity :
    BaseActivity<ActivityExpandableLinearLayoutBinding>(R.layout.activity_expandable_linear_layout) {

    private var ratio = 0f
    private var reversed = false

    override fun initData() {
//        mBinding.javaExpandableLinearLayout.outUseMethodChangeExpandText("显示更多")
//        mBinding.javaExpandableLinearLayout.outUseMethodChangeHideText("收起内容")
//
//        mBinding.kotlinExpandableLinearLayout.outUseMethodChangeExpandText("显示更多")
//        mBinding.kotlinExpandableLinearLayout.outUseMethodChangeHideText("收起内容")

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (ratio >= 1) {
                    reversed = true
                }
                if (ratio <= 0) {
                    reversed = false
                }
                ratio += if (reversed) -0.1f else 0.1f
                runOnUiThread { mBinding.slidingIndicatorBar.setBendingRatio(ratio) }
            }
        }, 800, 60)
    }

}