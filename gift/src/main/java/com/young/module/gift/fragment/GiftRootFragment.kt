package com.young.module.gift.fragment

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.giftRootFragment
import com.young.ext.initRecyclerView
import com.young.ext.initSmartRefreshLayout
import com.young.module.gift.R
import com.young.module.gift.databinding.ActivityGiftBinding
import com.young.utils.JsonHelper
import com.young.widget.bean.WidgetBean
import com.young.widget.bean.WidgetMultipleItem
import com.young.widget.utils.WidgetConvertHelper
import com.young.widget.utils.WidgetDataHelper
import java.util.ArrayList

@Route(path = giftRootFragment)
class GiftRootFragment : BaseFragment<ActivityGiftBinding>(R.layout.activity_gift) {

    private var mAdapter: GiftAdapter? = null
    private var dataList = ArrayList<WidgetMultipleItem>()

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
            val list = WidgetDataHelper.getItemType()
            for (i in list.indices) {
                addItemType(
                    list[i].type,
                    list[i].layoutResId
                )
            }
        }

        @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
        override fun convert(holder: BaseViewHolder, item: WidgetMultipleItem) {
            WidgetConvertHelper.convert(mBaseActivity(),holder, item)
        }
    }

}