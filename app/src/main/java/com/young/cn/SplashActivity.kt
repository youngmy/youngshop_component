package com.young.cn

import android.annotation.SuppressLint
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.young.base.activity.BaseActivity
import com.young.cn.databinding.ActivityStartBinding
import com.young.commonconfig.helper.*


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivityStartBinding>() {

    override fun getLayoutId(): Int {
//        installSplashScreen()
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).transparentBar().init()
    }

    override fun initData() {
//
        mBinding.translateTextView.post {
            //通过post拿到的tvTranslate.getWidth()不会为0。
            val translateAnimation =
                TranslateAnimation(0f, mBinding.translateTextView.width.toFloat(), 0f, 0f)
            translateAnimation.duration = 1000
            translateAnimation.fillAfter = true
            mBinding.translateTextView.startAnimation(translateAnimation)

            //动画监听
            translateAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    //动画结束时跳转到主页面
                    ARouter.getInstance().build(mainActivity).navigation()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }



}