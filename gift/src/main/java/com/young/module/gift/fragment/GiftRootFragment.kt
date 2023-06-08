package com.young.module.gift.fragment

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.giftRootFragment
import com.young.ext.initRecyclerView
import com.young.ext.initSmartRefreshLayout
import com.young.module.gift.R
import com.young.module.gift.databinding.ActivityGiftBinding
import com.young.module.gift.utils.GiftCommonClickHelper
import com.young.utils.JsonHelper
import com.young.utils.MyStatusBarUtil
import com.young.utils.TimeHelper
import com.young.view.MyTextView
import com.young.widget.adapter.MyBannerImageAdapter
import com.young.widget.adapter.WidgetBtnGridAdapter
import com.young.widget.adapter.WidgetBtnHorizontalAdapter
import com.young.widget.bean.CommonClickBean
import com.young.widget.bean.WidgetBean
import com.young.widget.bean.WidgetItemTypeBean
import com.young.widget.bean.WidgetMultipleItem
import com.young.widget.utils.CommonClickHelper
import com.young.widget.utils.TopBannerHelper
import com.young.widget.utils.WidgetConvertHelper
import com.young.widget.utils.WidgetDataHelper
import com.youth.banner.Banner
import java.util.ArrayList

@Route(path = giftRootFragment)
class GiftRootFragment : BaseFragment<ActivityGiftBinding>(R.layout.activity_gift) {

    private var mAdapter: GiftAdapter? = null
    private var dataList = ArrayList<WidgetMultipleItem>()

    override fun initView() {
        super.initView()
        mBinding.topLineView.setPadding(0, MyStatusBarUtil.getHeight(), 0, 0)
    }

    override fun initData() {
        initAdapter()
        initRefresh()
    }

    private fun initAdapter() {
        mAdapter = GiftAdapter(dataList)
        mBinding.recyclerView.initRecyclerView(mBaseActivity(), spanCount = 1)
        mBinding.recyclerView.adapter = mAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRefresh() {
        mBinding.refreshLayout.initSmartRefreshLayout(
            refreshListener = {
                dataList.clear()
                mAdapter?.notifyDataSetChanged()
                requestData()
            },
            loadMoreListener = {


                mBinding.refreshLayout.finishLoadMore()
//                if (isLoadMore) {
//                    pageIndex++
//                    isLoadMore = false
//                }
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun requestData() {
        val result = JsonHelper.readLocalJson(mBaseActivity(), "gift")
//        Log.i("data===", "===HomeFragment===result===${result}")
        val bean = JsonHelper.fromJson(result, WidgetBean::class.java)
        if (1 == bean.code) {
            dataList.addAll(WidgetDataHelper.getItemView(bean.data))
        }
        mAdapter?.notifyDataSetChanged()
        mBinding.refreshLayout.finishRefresh()
    }

    inner class GiftAdapter(data: MutableList<WidgetMultipleItem>) :
        BaseMultiItemQuickAdapter<WidgetMultipleItem, BaseViewHolder>(data) {

        init {
//            val list = WidgetDataHelper.getItemType()
//            for (i in list.indices) {
//                addItemType(
//                    list[i].type,
//                    list[i].layoutResId
//                )
//            }
            addItemType(
                WidgetMultipleItem.TOP_BANNER,
                com.young.module.libbase.R.layout.layout_top_banner
            )
            addItemType(
                    WidgetMultipleItem.BTN_GRID,
                    com.young.module.libbase.R.layout.layout_btn_grid
                )
            addItemType(
                    WidgetMultipleItem.DATE_SHOW,
                    com.young.module.libbase.R.layout.layout_date_show
                )
            addItemType(
                    WidgetMultipleItem.HORIZONTAL_LED,
                    com.young.module.libbase.R.layout.layout_horizontal_led
                )
            addItemType(
                    WidgetMultipleItem.VERTICAL_LED,
                    com.young.module.libbase.R.layout.layout_vertical_led
                )
            addItemType(
                    WidgetMultipleItem.MINE_HEADER,
                    com.young.module.libbase.R.layout.layout_mine_header
                )
            addItemType(
                    WidgetMultipleItem.BTN_HORIZONTAL,
                    com.young.module.libbase.R.layout.layout_btn_horizontal
                )
        }

        @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
        override fun convert(holder: BaseViewHolder, item: WidgetMultipleItem) {
            when (holder.itemViewType) {
//                WidgetMultipleItem.SEARCH -> {
//
//
//                }
                WidgetMultipleItem.TOP_BANNER -> {
                    val bannerView =
                        holder.getView<Banner<CommonClickBean, MyBannerImageAdapter<CommonClickBean>>>(
                            com.young.module.libbase.R.id.bannerView
                        )
                    TopBannerHelper.initTopBanner(
                        activity,
                        bannerView,
                        item.bean.content
                    ) {
                    }

                }
                WidgetMultipleItem.BTN_GRID -> {
                    val titleTextView = holder.getView<TextView>(com.young.module.libbase.R.id.titleTextView)
                    titleTextView.text = item.bean.title
                    val recyclerView = holder.getView<RecyclerView>(com.young.module.libbase.R.id.recyclerView)
                    val list = ArrayList<CommonClickBean>()
                    list.clear()
                    list.addAll(item.bean.content)
                    val mAdapter = WidgetBtnGridAdapter(list)
                    recyclerView.initRecyclerView(activity, spanCount = item.bean.lineNumber)
                    recyclerView.adapter = mAdapter
                    mAdapter.setOnItemClickListener { _, _, position ->
                        GiftCommonClickHelper.commonClick( activity,list[position])
                    }
                }
//                WidgetMultipleItem.DATE_SHOW -> {
//                    val yesterdayTextView = holder.getView<TextView>(R.id.yesterdayTextView)
//                    val todayTextView = holder.getView<TextView>(R.id.todayTextView)
//                    val tomorrowTextView = holder.getView<TextView>(R.id.tomorrowTextView)
//
//                    yesterdayTextView.text = "昨天：${TimeHelper.getDate(amount = -1)}"
//                    todayTextView.text = "今天：${TimeHelper.getDate()}"
//                    tomorrowTextView.text = "明天：${TimeHelper.getDate(amount = 1)}"
//                }
//                WidgetMultipleItem.HORIZONTAL_LED -> {
//                    val ledTextView = holder.getView<MyTextView>(R.id.ledTextView)
//                    ledTextView.text = item.bean.led
//                    ledTextView.init(activity?.windowManager)
//                    ledTextView.startScroll()
//                }
//                WidgetMultipleItem.MINE_HEADER -> {
//
//                }
//                WidgetMultipleItem.BTN_HORIZONTAL -> {
//                    val titleTextView = holder.getView<TextView>(R.id.titleTextView)
//                    titleTextView.text = item.bean.title
//                    val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
//                    val list = ArrayList<CommonClickBean>()
//                    list.clear()
//                    list.addAll(item.bean.content)
//                    val mAdapter = WidgetBtnHorizontalAdapter(list)
//                    recyclerView.initRecyclerView(activity, layoutManagerType = 1,orientation= LinearLayoutManager.VERTICAL)
////                recyclerView.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL_LIST))
//                    recyclerView.adapter = mAdapter
//                    mAdapter.setOnItemClickListener { _, _, position ->
//                        CommonClickHelper.commonClick( activity,list[position])
//                    }
//                }
                else -> {

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar
            .with(this)
            .statusBarView(mBinding.topLineView)
            .transparentStatusBar()//透明状态栏，不写默认透明色
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
            ImmersionBar
                .with(this)
                .statusBarView(mBinding.topLineView)
                .transparentStatusBar()//透明状态栏，不写默认透明色
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .init()
        }
    }

}