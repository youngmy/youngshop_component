package com.young.module.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.activity.BaseActivity
import com.young.base.adapter.FragmentViewPagerAdapter
import com.young.commonconfig.helper.*
import com.young.helper.ARouterHelper
import com.young.module.main.adapter.MainBottomTabAdapter
import com.young.module.main.bean.MainBottomTabBean
import com.young.module.main.databinding.ActivityMainBinding
import com.young.view.HeightDialog

@Route(path = mainActivity)
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var fragmentDataList = ArrayList<Fragment>()
    private var bottomTabDataList = ArrayList<MainBottomTabBean>()
    private var mAdapter: MainBottomTabAdapter? = null

    private var getCurrentPosition = 2

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mActivity: MainActivity

        fun startActivity(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context?.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        mActivity = this
//        HeightDialog(this).show()
    }

    override fun initData() {
        initTabLayout()
//        mBinding.textView.setOnClickListener {
//            Log.i("============", "=========MainActivity========")
//            ARouter.getInstance().build("/young/module/login/LoginActivity").navigation()

//                //liveData方式模拟
//                mViewModel.doHttpLiveData()
//
//
//                //回调方式模拟
//                mViewModel.doHttpCallback({
//                    //成功
//                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                }, {
//                    //失败
//                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                })

//        }
    }

    private fun initTabLayout(){
        fragmentDataList.clear()

        fragmentDataList.add(ARouterHelper.getFragment(homeRootFragment))
        fragmentDataList.add(ARouterHelper.getFragment(shopRootFragment))
        fragmentDataList.add(ARouterHelper.getFragment(giftRootFragment))
        fragmentDataList.add(ARouterHelper.getFragment(mineRootFragment))
        bottomTabDataList.add(MainBottomTabBean("首页",
            R.mipmap.home_selected,
            R.mipmap.home_normal,true))
        bottomTabDataList.add(MainBottomTabBean("商城",
            R.mipmap.shop_selected,
            R.mipmap.shop_normal,false))
        bottomTabDataList.add(MainBottomTabBean("礼品",
            R.mipmap.gift_selected,
            R.mipmap.gift_normal,false))
        bottomTabDataList.add(MainBottomTabBean("我的",
            R.mipmap.mine_selected,
            R.mipmap.mine_normal,false))
        initAdapter()
        initViewPager()
        setCurrentItem(getCurrentPosition)
    }

    private fun initViewPager() {
        mBinding.viewPager.adapter = FragmentViewPagerAdapter(this, fragmentDataList)
        mBinding.viewPager.isUserInputEnabled = false
        mBinding.viewPager.offscreenPageLimit=3
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                LogHelper.i("data===", "===position===${position}")
            }
        })
//        LogHelper.i("data===", "===bottomTabDataList.size===${bottomTabDataList.size}")
//        tabRecyclerView?.initRecyclerView(mBaseActivity(), spanCount = bottomTabDataList.size)
        mBinding.tabRecyclerView.layoutManager= GridLayoutManager(mBaseActivity(), bottomTabDataList.size)

    }

    private fun initAdapter() {
        mAdapter = MainBottomTabAdapter(bottomTabDataList)
        mBinding.tabRecyclerView.adapter = mAdapter
        mAdapter?.setOnItemClickListener { _, _, position ->
            setCurrentItem(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCurrentItem(item: Int, smoothScroll: Boolean = false) {
        getCurrentPosition = item
        mBinding.tabRecyclerView.scrollToPosition(item)
        mBinding.viewPager.setCurrentItem(item, smoothScroll)
        for (i in bottomTabDataList.indices) {
            bottomTabDataList[i].selectState = i == item
        }
        mAdapter?.notifyDataSetChanged()
//        setCurrentTopColor(getCurrentPosition)
    }

//    /**
//     * 重写observeLiveData方法，监听LiveData发生的改变
//     */
//    override fun observeLiveData() {
//        super.observeLiveData()
//        mViewModel.getLiveData()?.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun dialogLoading() {
//        super.dialogLoading()
//        //这里进行Loading加载
//    }
//
//    override fun dialogDismiss() {
//        super.dialogDismiss()
//        //这里进行Loading销毁
//    }
}