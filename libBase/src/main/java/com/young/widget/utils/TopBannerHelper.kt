package com.young.widget.utils

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.young.utils.HeightHelper
import com.young.widget.adapter.MyBannerImageAdapter
import com.young.widget.bean.CommonClickBean
import com.youth.banner.Banner
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

object TopBannerHelper {


    fun initTopBanner(
        activity: FragmentActivity?,
        bannerView: View?,
        list: List<CommonClickBean> = arrayListOf(),
        callBack: (Int) -> Unit
    ) {
        @Suppress("UNCHECKED_CAST")
        val banner =
            (bannerView as Banner<CommonClickBean, MyBannerImageAdapter<CommonClickBean>>)
        HeightHelper.setHeightInLinearLayout(bannerView, 375, 200)
        banner.apply {
            addBannerLifecycleObserver(activity)
            indicator = CircleIndicator(activity)
            setAdapter(object :
                MyBannerImageAdapter<CommonClickBean>(list) {
                override fun onBindView(
                    holder: BannerImageHolder,
                    bean: CommonClickBean,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(activity!!)
                        .load(bean.imageUrl)
                        .into(holder.imageView)
                    holder.imageView?.setOnClickListener {
                        CommonClickHelper.commonClick( activity,bean)
                        callBack.invoke(position)
                    }
                }
            })
        }
    }

}