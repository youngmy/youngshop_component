package com.young.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.WindowManager
import com.young.module.libbase.R
import com.young.module.libbase.databinding.DialogHeightRulerBinding

@Suppress("DEPRECATION")
class HeightDialog(context: Context) : Dialog(context, R.style.UIAlertViewStyle) {
    private lateinit var mBinding: DialogHeightRulerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogHeightRulerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //点击弹窗外侧关闭弹窗
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        val windowManager: WindowManager? = window?.windowManager
        val lp: WindowManager.LayoutParams? = window?.attributes
        //所有在这个window之后的会变暗,使用dimAmount属性来控制变暗的程度(1.0不透明,0.0完全透明)
        lp?.alpha = 1f
        lp?.dimAmount = 0.5f
        window?.attributes = lp
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        //设置窗口的占比
        val display: Display? = windowManager?.defaultDisplay
        (display?.height)?.div(2.2)
            ?.let { window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, it.toInt()) }
        //设置弹窗位置于屏幕底部
        window?.attributes?.gravity = Gravity.BOTTOM


        mBinding.rulerHeight.setTextChangedListener {
            //得到身高的最终值
            mBinding.tvRegisterInfoHeightValue.text = it.toString()
        }

        //关闭
        mBinding.closeImage.setOnClickListener {
            this.dismiss()
        }
        //确定
        mBinding.required.setOnClickListener {
            this.dismiss()
        }

    }
}