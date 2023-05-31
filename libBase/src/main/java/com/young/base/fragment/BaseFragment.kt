package com.young.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *Fragment父类
 */
abstract class BaseFragment<VB : ViewDataBinding>(@LayoutRes layoutId: Int = 0) :
    Fragment(layoutId) {

    private var isLoaded = false
    private var firstVisit = true

    fun arguments() = arguments ?: Bundle()
    fun mBaseActivity() = mBaseActivity!!

    var mBaseActivity: FragmentActivity? = null

    lateinit var mBinding: VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBaseActivity = activity
        firstVisit = true
        mBinding = DataBindingUtil.bind(view)!!
//        initView()
//        initData()
    }

    open fun initView() {}

    abstract fun initData()

    override fun onResume() {
        super.onResume()
        //增加了Fragment是否可见的判断
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded=true
        }
        if (firstVisit) {
            firstVisit = false
        } else {
            onReStart()
        }

    }

    private fun lazyInit() {
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    open fun onReStart() {}

    protected fun loadRootFragment(@IdRes containerId: Int, toFragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(containerId, toFragment)
        transaction.commit()
    }

}