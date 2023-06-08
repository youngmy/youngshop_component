package com.young.module.demo

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.young.base.activity.BaseActivity
import com.young.commonconfig.helper.*
import com.young.module.demo.databinding.ActivityDemoBinding

@Route(path = demoActivity)
class DemoActivity : BaseActivity<ActivityDemoBinding>(R.layout.activity_demo) {


    override fun initData() {
        mBinding.staggerdRecyclerView.setOnClickListener {
            ARouter.getInstance().build(staggerdRecyclerViewActivity).navigation()
        }
        mBinding.dataStoreView.setOnClickListener {
            ARouter.getInstance().build(dataStoreActivity).navigation()
        }
        mBinding.changeLanguageView.setOnClickListener {
            ARouter.getInstance().build(changeLanguageActivity).navigation()
        }
        mBinding.expandableLinearLayoutView.setOnClickListener {
            ARouter.getInstance().build(expandableLinearLayoutActivity).navigation()
        }


    }


}