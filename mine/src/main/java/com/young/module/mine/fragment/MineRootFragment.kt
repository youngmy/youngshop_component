package com.young.module.mine.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.donkingliang.consecutivescroller.ConsecutiveScrollerLayout
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.mineRootFragment
import com.young.module.mine.R
import com.young.module.mine.Test
import com.young.module.mine.databinding.ActivityMineBinding
import com.young.utils.CommonMsgPromptDialogHelper
import com.young.utils.MyStatusBarUtil
import com.young.utils.SizeUtils
import com.young.view.dialog.MyAlertDialog
import com.gyf.immersionbar.ImmersionBar
import com.young.decoration.SpacesItemDecoration
import com.young.module.mine.adapter.GoodsListAdapter
import com.young.module.mine.bean.GoodsBean
import com.young.module.mine.bean.MenuBean
import com.young.module.mine.view.FiveMenuView
import com.young.utils.DisplayUtil
import com.young.utils.PixelUtil

@Route(path = mineRootFragment)
class MineRootFragment : BaseFragment<ActivityMineBinding>(R.layout.activity_mine) {

    private val goodsListAdapter by lazy { GoodsListAdapter(arrayListOf()) }

    private val staggeredGridLayoutManager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
    private val fiveMenuView: FiveMenuView by lazy { FiveMenuView(this.requireContext(), this@MineRootFragment) }

    override fun initView() {
        super.initView()
        initStatusHeight()
        mBinding.mineRefreshLayout.run {
            setEnableRefresh(false)
            setEnableLoadMore(false)
        }
    }


    override fun initData() {
//        mBinding.shareWeChat.setOnClickListener {
//    WeChatHelper.shareTextToWeChat(mBaseActivity(),"微信")
//            test(mBaseActivity())
//        }
//        mBinding.shareWeChatFriend.setOnClickListener {
//            WeChatHelper.shareTextToWeChatFriend(mBaseActivity(), Message(
//                title = "标题",
//
//            ))
//            CommonMsgPromptDialogHelper.msgPrompt(mBaseActivity(), title = "标题", content = "林心如作为台湾的顶级女星，曾最终着“紫薇”角色进入国内市场，顺利婚了！"){_,_->
//
//            }
//        }

        goodsListAdapter.loadMoreModule.setOnLoadMoreListener{
//            viewModel.loadMoreRecommendList()
            val dataList=ArrayList<GoodsBean>()
            dataList.add(GoodsBean("","1","","","","","1"))
            dataList.add(GoodsBean("","2","","","","","2"))
            dataList.add(GoodsBean("","3","","","","","1"))
            dataList.add(GoodsBean("","4","","","","","2"))
            dataList.add(GoodsBean("","5","","","","","1"))
            dataList.add(GoodsBean("","6","","","","","2"))
            dataList.add(GoodsBean("","7","","","","","1"))
            dataList.add(GoodsBean("","8","","","","","2"))
            goodsListAdapter.setList(dataList)
            goodsListAdapter.loadMoreModule.loadMoreComplete()
        }
        mBinding.functionList.run {
            removeAllViews()
            addView(fiveMenuView)
        }

        mBinding.consecutiveScrollerLayout.onVerticalScrollChangeListener =
            ConsecutiveScrollerLayout.OnScrollChangeListener { _, scrollY, _, _ -> handleScroll((scrollY * 0.65).toInt()) }

        mBinding.recommendGoodsList.run {
//            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE //解决加载下一页后重新排列的问题
            addItemDecoration(SpacesItemDecoration(10))
            layoutManager = staggeredGridLayoutManager
            adapter = goodsListAdapter
        }
        goodsListAdapter.setOnItemClickListener { adapter, view, position ->
//            ARouter.getInstance().build(RouterPaths.GOODS_DETAIL).navigation()
        }
        mBinding.backTopView.setOnClickListener {
            mBinding.consecutiveScrollerLayout.scrollToChild(mBinding.consecutiveScrollerLayout.getChildAt(0))
        }
        mBinding.floatingHeaderLayout.settingImageView.setOnClickListener {

        }
        mBinding.floatingHeaderLayout.messageImageView.setOnClickListener{

        }
        val fiveMenuList=ArrayList<MenuBean>()
        fiveMenuList.add(MenuBean("","1",""))
        fiveMenuList.add(MenuBean("","2",""))
        fiveMenuList.add(MenuBean("","3",""))
        fiveMenuList.add(MenuBean("","4",""))
        fiveMenuList.add(MenuBean("","5",""))
        fiveMenuList.add(MenuBean("","6",""))
        fiveMenuList.add(MenuBean("","7",""))
        fiveMenuList.add(MenuBean("","8",""))
        fiveMenuList.add(MenuBean("","9",""))
        fiveMenuList.add(MenuBean("","10",""))
        fiveMenuList.add(MenuBean("","11",""))
        fiveMenuView.setData(fiveMenuList)
          val dataList=ArrayList<GoodsBean>()
        dataList.add(GoodsBean("","11","","","","","1"))
        dataList.add(GoodsBean("","22","","","","","2"))
        dataList.add(GoodsBean("","33","","","","","1"))
        dataList.add(GoodsBean("","44","","","","","2"))
        dataList.add(GoodsBean("","55","","","","","1"))
        dataList.add(GoodsBean("","66","","","","","2"))
        dataList.add(GoodsBean("","77","","","","","1"))
        dataList.add(GoodsBean("","88","","","","","2"))
        goodsListAdapter.setList(dataList)
        goodsListAdapter.loadMoreModule.loadMoreComplete()

        mBinding.tabLayout.removeAllTabs()
        val tabList= mutableListOf("全部","新闻","视频","全部","新闻","视频","全部","新闻","视频","全部","新闻","视频","全部","新闻","视频")
        tabList.forEach { v ->
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(v))
        }
    }



    private fun initStatusHeight(){
        mBinding.headerLayout.setPadding(0, MyStatusBarUtil.getHeight(), 0, 0)
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar
            .with(this)
            .transparentStatusBar()//透明状态栏，不写默认透明色
            .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
            .init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) {
            ImmersionBar
                .with(this)
                .transparentStatusBar()//透明状态栏，不写默认透明色
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .init()
        }
    }

    private val userInfoLayoutMaxMarginTop = PixelUtil.toPixelFromDIP(40f)
    private val userInfoLayoutMinMarginTop = PixelUtil.toPixelFromDIP(10f)
    private val maxHeight = PixelUtil.toPixelFromDIP(58f).toInt()
    private val minHeight = PixelUtil.toPixelFromDIP(25f).toInt()

    private fun handleScroll(
        scrollY: Int,
    ) {
        //处理背景色、用户信息显示与隐藏
        if (scrollY >= MyStatusBarUtil.getHeight()) {
            mBinding.floatingHeaderLayout.userInfoLayout.visibility = View.GONE
            mBinding.headerLayout.setBackgroundColor(resources.getColor(R.color.white))
            mBinding.floatingHeaderLayout.mineTextView.setTextColor(resources.getColor(com.young.module.libbase.R.color.base_color_0))
            mBinding.bgRootLayout.setBackgroundResource(R.drawable.banner_bg)
        } else {
            mBinding.floatingHeaderLayout.userInfoLayout.visibility = View.VISIBLE
            mBinding.headerLayout.setBackgroundColor(resources.getColor(com.young.module.libbase.R.color.transparent))
            mBinding.floatingHeaderLayout.mineTextView.setTextColor(resources.getColor(com.young.module.libbase.R.color.transparent))
            mBinding.bgRootLayout.setBackgroundResource(R.drawable.mine_top_bg)
        }
        //用户信息Layout距离顶部距离
        val userInfoLayoutNewMarginTop = userInfoLayoutMaxMarginTop - scrollY
        val userInfoLp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        if (userInfoLayoutNewMarginTop <= userInfoLayoutMinMarginTop){
            userInfoLp.topMargin = userInfoLayoutMinMarginTop.toInt()
        } else {
            userInfoLp.topMargin = userInfoLayoutNewMarginTop.toInt()
        }
        mBinding.floatingHeaderLayout.userInfoLinearLayout.layoutParams = userInfoLp

        //处理头像大小
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val height =  if (maxHeight - scrollY < minHeight) minHeight else maxHeight - scrollY

        lp.height = height
        lp.width = height
        lp.leftMargin = PixelUtil.toPixelFromDIP(20f).toInt()

        mBinding.floatingHeaderLayout.profileImage.layoutParams = lp
        //改变tabLayout背景色
        if (mBinding.consecutiveScrollerLayout.currentStickyViews.indexOfFirst { v -> v.id == R.id.tabLayout } == -1) {
            mBinding.tabLayout.setBackgroundResource(com.young.module.libbase.R.color.base_color_F5)
        } else {
            mBinding.tabLayout.setBackgroundResource(R.color.white)
        }
        if (scrollY == 0) {
            mBinding.tabLayout.setBackgroundResource(com.young.module.libbase.R.color.base_color_F5)
        }

        //显示backToTop
        if (scrollY >= DisplayUtil.getScreenHeight(this.requireContext())) {
            mBinding.backTopView.visibility = View.VISIBLE
        } else {
            mBinding.backTopView.visibility = View.GONE
        }
    }

    fun test(context: Context?) {
        var privacyPolicyDialog: MyAlertDialog? = null
        val builder = MyAlertDialog.Builder(context)
            .addDefaultAnimation()
            .setCancelable(true)
            .setContentView(R.layout.dialog_privacy_policy)
            .setWidthAndHeight(
                SizeUtils.dp2px(context, 280f),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setOnClickListener(R.id.tv_privacy_policy) { v: View? ->
                privacyPolicyDialog?.dismiss()
            }.setOnClickListener(
                R.id.tv_no_used
            ) { v: View? ->
//                    ToastUtils.showShortToast(context,"不同意隐私政策，无法正常使用App，请退出App，重新进入。");
                privacyPolicyDialog?.dismiss()
            }.setOnClickListener(
                R.id.tv_agree
            ) { v: View? ->
                //已同意隐私政策
                privacyPolicyDialog?.dismiss()
            }
        privacyPolicyDialog = builder.create()
        privacyPolicyDialog.show()
    }
}