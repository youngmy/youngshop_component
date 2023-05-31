package com.young.helper

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

object ARouterHelper {

    fun getFragment(path:String):Fragment{
      return  ARouter.getInstance().build(path).navigation() as Fragment
    }

}