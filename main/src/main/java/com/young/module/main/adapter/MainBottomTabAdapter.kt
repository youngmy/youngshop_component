package com.young.module.main.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.young.module.main.R
import com.young.module.main.bean.MainBottomTabBean
import com.young.module.main.databinding.ItemMainBottomTabBinding

//class MainBottomTabAdapter(data: MutableList<MainBottomTabBean>) :
//    BaseQuickAdapter<MainBottomTabBean, BaseViewHolder>(R.layout.item_main_bottom_tab, data) {
//
//    override fun convert(holder: BaseViewHolder, item: MainBottomTabBean) {
//
//        val iconImageView = holder.getView<ImageView>(R.id.iconImageView)
//        val titleTextView = holder.getView<TextView>(R.id.titleTextView)
//
//        iconImageView.setImageResource(
//            if (item.selectState) {
//                item.selectImage
//            } else {
//                item.normalImage
//            }
//        )
//        titleTextView.apply {
//            text = item.title
//            setTextColor(
//                if (item.selectState) {
//                    Color.parseColor("#FF1706")
//                } else {
//                    Color.parseColor("#666666")
//                }
//            )
//        }
//
//    }
//
//}

class MainBottomTabAdapter(data: MutableList<MainBottomTabBean>)
    : BaseQuickAdapter<MainBottomTabBean,
        BaseDataBindingHolder<ItemMainBottomTabBinding>>(R.layout.item_main_bottom_tab,data) {

    override fun convert(holder: BaseDataBindingHolder<ItemMainBottomTabBinding>, item: MainBottomTabBean) {

       holder.dataBinding?.also {
           it.bean=item
           if (item.selectState) {
               it.imageUrl=item.selectImage
           } else {
               it.imageUrl= item.normalImage
           }
           it.executePendingBindings()
        }



    }

}