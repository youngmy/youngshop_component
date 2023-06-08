package com.young.module.home.fragment

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.helper.YLogHelper
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.homeRootFragment
import com.young.ext.initRecyclerView
import com.young.ext.initSmartRefreshLayout
import com.young.module.home.R
import com.young.module.home.databinding.ActivityHomeBinding
import com.young.utils.JsonHelper
import com.young.utils.MVUtils
import com.young.utils.MyStatusBarUtil
import com.young.widget.bean.WidgetBean
import com.young.widget.bean.WidgetMultipleItem
import com.young.widget.utils.WidgetConvertHelper
import com.young.widget.utils.WidgetDataHelper
import me.jessyan.autosize.internal.CustomAdapt


@Route(path = homeRootFragment)
class HomeRootFragment:BaseFragment<ActivityHomeBinding>(R.layout.activity_home), CustomAdapt {

    private var mAdapter: HomeAdapter? = null
    private var dataList = ArrayList<WidgetMultipleItem>()

    override fun initView() {
        super.initView()
        initStatusHeight()
    }

    override fun initData() {
        initAdapter()
        initRefresh()
        //存
        YLogHelper.i("HomeRootFragment===", " 存")
        MVUtils.put("age", 24)
        //取
        val age = MVUtils.getInt("age", 0)
        YLogHelper.i("HomeRootFragment===", "取 ：age===$age")

    }

    private fun initStatusHeight(){
        mBinding.topLineView.setPadding(0, MyStatusBarUtil.getHeight(), 0, 0)
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

    private fun initAdapter() {
        mAdapter = HomeAdapter(dataList)
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
        val result = JsonHelper.readLocalJson(mBaseActivity(), "home")
//        Log.i("data===", "===HomeFragment===result===${result}")
        val bean = JsonHelper.fromJson(result, WidgetBean::class.java)
        if (1 == bean.code) {
            dataList.addAll(WidgetDataHelper.getItemView(bean.data))
        }
        mAdapter?.notifyDataSetChanged()
        mBinding.refreshLayout.finishRefresh()
    }

    inner class HomeAdapter(data: MutableList<WidgetMultipleItem>) :
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

    override fun isBaseOnWidth(): Boolean {
       return false
    }

    override fun getSizeInDp(): Float {
        return 750F
    }

}