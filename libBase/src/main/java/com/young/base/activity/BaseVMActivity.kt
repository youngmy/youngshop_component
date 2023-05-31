package com.young.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.young.base.config.StateLayoutEnum
import com.young.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 *使用ViewModel父类Activity
 */
abstract class BaseVMActivity<VB : ViewDataBinding, BM : BaseViewModel>(@LayoutRes layoutId: Int = 0) :
    BaseActivity<VB>(layoutId) {

    lateinit var mViewModel: BM

    override fun initData() {
        mViewModel = getViewModel()!!
        val variableId = getVariableId()
        if (variableId != -1) {
            mBinding.setVariable(getVariableId(), mViewModel)
            mBinding.executePendingBindings()
        }
        initVMData()
        observeLiveData()
        initState()
        lifecycle.addObserver(mViewModel)
    }

    /**
     * 获取绑定的xml variable
     */
    open fun getVariableId(): Int {
        return -1
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:初始化状态
     */
    private fun initState() {
        mViewModel.mStateViewLiveData.observe(this) {
            when (it) {
                StateLayoutEnum.DIALOG_LOADING -> {
                    dialogLoading()
                }
                StateLayoutEnum.DIALOG_DISMISS -> {
                    dialogDismiss()
                }
                StateLayoutEnum.DATA_ERROR -> {
                    dataError()
                }
                StateLayoutEnum.DATA_NULL -> {
                    dataEmpty()
                }
                StateLayoutEnum.NET_ERROR -> {
                    netError()
                }
                StateLayoutEnum.HIDE -> {
                    hide()
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    abstract fun initVMData()

    /**
     * LiveData的Observer
     */
    open fun observeLiveData() {

    }

    /**
     * dialog加载
     */
    open fun dialogLoading() {}

    /**
     * dialog隐藏
     */
    open fun dialogDismiss() {}

    /**
     * 数据错误
     */
    open fun dataError() {}

    /**
     * 数据为空
     */
    open fun dataEmpty() {}

    /**
     * 网络错误或请求错误
     */
    open fun netError() {}

    /**
     * 隐藏某些布局或者缺省页等
     */
    open fun hide() {}

    private fun getViewModel(): BM? {
        //这里获得到的是类的泛型的类型
        val type = javaClass.genericSuperclass
        if (type != null && type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            return ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )
                .get(tClass as Class<BM>)
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            lifecycle.removeObserver(mViewModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}