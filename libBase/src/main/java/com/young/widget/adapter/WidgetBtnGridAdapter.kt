package com.young.widget.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.young.module.libbase.R
import com.young.utils.ImageViewHelper
import com.young.widget.bean.CommonClickBean

class WidgetBtnGridAdapter(data: MutableList<CommonClickBean>) :
    BaseQuickAdapter<CommonClickBean, BaseViewHolder>(R.layout.item_widget_btn_grid, data) {

    override fun convert(holder: BaseViewHolder, item: CommonClickBean) {

        val iconImageView = holder.getView<ImageView>(R.id.iconImageView)
//        GlideHelper.loadImage(mContext,iconImageView,bean.img)
        iconImageView.setImageResource(ImageViewHelper.getImageViewId(item.imageUrl))
        holder.setText(R.id.titleTextView,item.title)

    }

}