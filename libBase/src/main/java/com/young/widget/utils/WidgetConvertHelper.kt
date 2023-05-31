package com.young.widget.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.young.ext.initRecyclerView
import com.young.module.libbase.R
import com.young.utils.TimeHelper
import com.young.view.MyTextView
import com.young.widget.adapter.MyBannerImageAdapter
import com.young.widget.adapter.WidgetBtnGridAdapter
import com.young.widget.adapter.WidgetBtnHorizontalAdapter
import com.young.widget.bean.CommonClickBean
import com.young.widget.bean.WidgetMultipleItem
import com.youth.banner.Banner

object WidgetConvertHelper {

    @SuppressLint("SetTextI18n")
    fun convert(activity: FragmentActivity?, holder: BaseViewHolder, item: WidgetMultipleItem) {
        when (holder.itemViewType) {
            WidgetMultipleItem.SEARCH -> {


            }
            WidgetMultipleItem.TOP_BANNER -> {
                val bannerView =
                    holder.getView<Banner<CommonClickBean, MyBannerImageAdapter<CommonClickBean>>>(
                        R.id.bannerView
                    )
                TopBannerHelper.initTopBanner(
                    activity,
                    bannerView,
                    item.bean.content
                ) {
                }

            }
            WidgetMultipleItem.BTN_GRID -> {
                val titleTextView = holder.getView<TextView>(R.id.titleTextView)
                titleTextView.text = item.bean.title
                val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
                val list = ArrayList<CommonClickBean>()
                list.clear()
                list.addAll(item.bean.content)
                val mAdapter = WidgetBtnGridAdapter(list)
                recyclerView.initRecyclerView(activity, spanCount = item.bean.lineNumber)
                recyclerView.adapter = mAdapter
                mAdapter.setOnItemClickListener { _, _, position ->
                    CommonClickHelper.commonClick( activity,list[position])
                }
            }
            WidgetMultipleItem.DATE_SHOW -> {
                val yesterdayTextView = holder.getView<TextView>(R.id.yesterdayTextView)
                val todayTextView = holder.getView<TextView>(R.id.todayTextView)
                val tomorrowTextView = holder.getView<TextView>(R.id.tomorrowTextView)

                yesterdayTextView.text = "昨天：${TimeHelper.getDate(amount = -1)}"
                todayTextView.text = "今天：${TimeHelper.getDate()}"
                tomorrowTextView.text = "明天：${TimeHelper.getDate(amount = 1)}"
            }
            WidgetMultipleItem.HORIZONTAL_LED -> {
                val ledTextView = holder.getView<MyTextView>(R.id.ledTextView)
                ledTextView.text = item.bean.led
                ledTextView.init(activity?.windowManager)
                ledTextView.startScroll()
            }
            WidgetMultipleItem.MINE_HEADER -> {

            }
            WidgetMultipleItem.BTN_HORIZONTAL -> {
                val titleTextView = holder.getView<TextView>(R.id.titleTextView)
                titleTextView.text = item.bean.title
                val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
                val list = ArrayList<CommonClickBean>()
                list.clear()
                list.addAll(item.bean.content)
                val mAdapter = WidgetBtnHorizontalAdapter(list)
                recyclerView.initRecyclerView(activity, layoutManagerType = 1,orientation= LinearLayoutManager.VERTICAL)
//                recyclerView.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL_LIST))
                recyclerView.adapter = mAdapter
                mAdapter.setOnItemClickListener { _, _, position ->
                    CommonClickHelper.commonClick( activity,list[position])
                }
            }
            else -> {

            }
        }
    }

}