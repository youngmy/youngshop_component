package com.young.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.young.base.config.StateLayoutEnum
import com.young.base.observer.BaseObserver

/**
 *viewModel父类
 */
open class BaseViewModel : ViewModel(), BaseObserver {
    /**
     * 控制状态视图的LiveData
     */
    val mStateViewLiveData = MutableLiveData<StateLayoutEnum>()

    /**
     * 更改状态视图的状态
     */
    public fun changeStateView(
        state: StateLayoutEnum
    ) {
        // 对参数进行校验
        when (state) {
            StateLayoutEnum.DIALOG_LOADING -> {
                mStateViewLiveData.postValue(StateLayoutEnum.DIALOG_LOADING)
            }
            StateLayoutEnum.DIALOG_DISMISS -> {
                mStateViewLiveData.postValue(StateLayoutEnum.DIALOG_DISMISS)
            }
            StateLayoutEnum.DATA_ERROR -> {
                mStateViewLiveData.postValue(StateLayoutEnum.DATA_ERROR)
            }
            StateLayoutEnum.DATA_NULL -> {
                mStateViewLiveData.postValue(StateLayoutEnum.DATA_NULL)
            }
            StateLayoutEnum.NET_ERROR -> {
                mStateViewLiveData.postValue(StateLayoutEnum.NET_ERROR)
            }
            StateLayoutEnum.HIDE -> {
                mStateViewLiveData.postValue(StateLayoutEnum.HIDE)
            }
        }

    }

    /**
     * 获取Repository
     */
    inline fun <reified R> getRepository(): R? {
        try {
            val clazz = R::class.java
            return clazz.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * 生命周期初始化
     */
    override fun onCreate() {
    }

    /**
     * 生命周期页面可见
     */
    override fun onStart() {
    }

    /**
     * 生命周期页面获取焦点
     */
    override fun onResume() {
    }

    /**
     * 生命周期页面失去焦点
     */
    override fun onPause() {

    }

    /**
     * 生命周期页面不可见
     */
    override fun onStop() {
        
    }

    /**
     * 生命周期页面销毁
     */
    override fun onDestroy() {
    }

}