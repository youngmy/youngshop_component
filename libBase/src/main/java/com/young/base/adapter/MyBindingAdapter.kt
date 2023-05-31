package com.young.base.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/** 设置 [view] 的选中状态为 [selected] */
@BindingAdapter("android:bind_selected", requireAll = false)
fun setViewSelected(view: View, selected: Boolean?) {
    if (view.isSelected == selected) {
        return
    }
    view.isSelected = selected!!
}



@BindingAdapter("android:local_image", requireAll = false)
fun ImageView.showImageResource(
    imageUrl: Int = 0
) {
    setImageResource(imageUrl)
}

@BindingAdapter("custom_url", requireAll = false)
fun ImageView.showImage(
    imageUrl: String? = null
) {
    var mImageUrl = ""
    imageUrl?.let {
        mImageUrl = it
    }
//    Glide.with(context).load(mImageUrl).into(this)
}



@BindingAdapter("header_url", requireAll = false)
fun ImageView.showHeaderImage(
    imageUrl: String? = null
) {
    var mImageUrl = ""
    imageUrl?.let {
        mImageUrl = it
    }
//    Glide.with(context).load(mImageUrl).into(this)
}


