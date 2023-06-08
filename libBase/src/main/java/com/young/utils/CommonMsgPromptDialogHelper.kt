package com.young.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.young.ext.goneViews
import com.young.ext.showViews
import com.young.module.libbase.R
import com.young.view.ScrollingTextViewTouchListener
import com.young.view.dialog.CustomDialog


object CommonMsgPromptDialogHelper {


    @SuppressLint("ClickableViewAccessibility")
    inline fun msgPrompt(
        context: Context?,
        title: String = "温馨提示",
        content: String = "",
        cancelName: String = "取消", confirmName: String = "确定",
        cancelable: Boolean = true,
        showTopLine: Boolean = true, showCenterLine: Boolean = true,
        showCancel: Boolean = true, showConfirm: Boolean = true,
        crossinline callBack: (Int, String) -> Unit
    ) {

        val mCustomDialog = CustomDialog(context, R.layout.dialog_common_msg_prompt)
        mCustomDialog.gravity = Gravity.CENTER
        mCustomDialog.setCancelable(cancelable)
        mCustomDialog.show()

        val titleTextView = mCustomDialog.getView(R.id.titleTextView) as TextView
        val contentTextView = mCustomDialog.getView(R.id.contentTextView) as TextView
        val topLineView = mCustomDialog.getView(R.id.topLineView) as View
        val centerLineView = mCustomDialog.getView(R.id.centerLineView) as View
        val cancelTextView = mCustomDialog.getView(R.id.cancelTextView) as TextView
        val confirmTextView = mCustomDialog.getView(R.id.confirmTextView) as TextView


        titleTextView.text = title
        contentTextView.text = content
        contentTextView.movementMethod = ScrollingMovementMethod.getInstance()
        contentTextView.setOnTouchListener(ScrollingTextViewTouchListener())
        cancelTextView.text = cancelName
        confirmTextView.text = confirmName
        if (showTopLine) {
            showViews(topLineView)
        } else {
            goneViews(topLineView)
        }

        if (showCenterLine) {
            showViews(centerLineView)
        } else {
            goneViews(centerLineView)
        }

        if (showCancel) {
            showViews(cancelTextView)
        } else {
            goneViews(cancelTextView)
        }

        if (showConfirm) {
            showViews(confirmTextView)
        } else {
            goneViews(confirmTextView)
        }

        cancelTextView.setOnClickListener {
            callBack.invoke(0, "cancelTextView")
            mCustomDialog.dismiss()
        }
        confirmTextView.setOnClickListener {
            callBack.invoke(1, "confirmTextView")
            mCustomDialog.dismiss()
        }


    }

}