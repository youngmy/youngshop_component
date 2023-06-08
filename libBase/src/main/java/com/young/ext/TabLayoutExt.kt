//package com.ext
//
//import android.app.Activity
//import android.util.TypedValue
//import com.google.android.material.tabs.TabLayout
//import com.jiameng.R
//import com.utils.ColorHelper
//import kotlinx.android.synthetic.main.layout_diy_tab_layout.view.*
//
//fun TabLayout.initTabLayout(
//    activity: Activity?,
//    tabLayout: TabLayout?,
//    titleList: ArrayList<String> = arrayListOf(),
//    position: Int = -1,
//    backgroundColor: Int = R.color.white,
//    normalColor: Int = R.color.black_3,
//    selectedColor: Int = R.color.main_color,
//    indicatorColor: Int = R.color.main_color,
//    indicatorHeight: Int = -4,
//    mTabMode: Int = 0,//MODE_SCROLLABLE = 0,MODE_FIXED = 1,MODE_AUTO = 2
//    diyType:String="1",
//    callBack: (Int) -> Unit
//): TabLayout {
//    removeAllTabs()
//    titleList.apply {
//        forEachIndexed { index, bean ->
//            if ("1"==diyType){
//                val itemView = activity!!.layoutInflater.inflate(
//                    R.layout.layout_diy_tab_layout,
//                    tabLayout,
//                    false
//                )
//                itemView.rootView.titleTextView.text = bean
//                itemView.rootView.titleTextView?.apply {
//                    setTextColor(
//                        ColorHelper.getColor(
//                            activity,
//                            if (0 == index) {
//                                selectedColor
//                            } else {
//                                normalColor
//                            }
//
//                        )
//                    )
//                    setTextSize(
//                        TypedValue.COMPLEX_UNIT_SP, if (0 == index) {
//                            15.toFloat()
//                        } else {
//                            13.toFloat()
//                        }
//                    )
//                }
//                newTab().apply {
//                    customView = itemView
//                    addTab(this)
//                }
//            }else{
//                newTab().apply {
//                    addTab(this.setText(bean))
//                }
//            }
//
//        }
//    }
//
//    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//        override fun onTabReselected(tab: TabLayout.Tab?) {
//        }
//
//        override fun onTabUnselected(tab: TabLayout.Tab?) {
//            //非选中时的样式
//            if ("1"==diyType){
//                tab?.view?.titleTextView?.apply {
//                    setTextColor(
//                        ColorHelper.getColor(
//                            activity!!,
//                            normalColor
//                        )
//                    )
//                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.toFloat())
//                }
//            }
//        }
//
//        override fun onTabSelected(tab: TabLayout.Tab?) {
//            if ("1"==diyType){
//                tab?.view?.titleTextView?.apply {
//                    setTextColor(
//                        ColorHelper.getColor(
//                            activity!!,
//                            selectedColor
//                        )
//                    )
//                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.toFloat())
//                }
//            }
//            //Tab选中的样式
//            callBack.invoke(tab?.position!!)
////                LogHelper.i("data====", "===position===${tab.position}")
//        }
//    })
//    if (position > 0) {
//        postDelayed({
//            getTabAt(position)?.select()
//        }, 1000)
//    }
//
//    setBackgroundColor(ColorHelper.getColor(activity!!, backgroundColor))
//
//    setSelectedTabIndicatorColor(
//        ColorHelper.getColor(
//            activity,
//            indicatorColor
//        )
//    )
//
//    if ("1"==diyType){
//        setSelectedTabIndicator(R.drawable.tab_layout_indicator_bg)
//    }
//
//    isTabIndicatorFullWidth = false
//    if ("0"==diyType){
//        setTabTextColors(
//            ColorHelper.getColor(activity, normalColor),
//            ColorHelper.getColor(activity, selectedColor)
//        )
//    }
//
//    tabRippleColor = null
//    @Suppress("DEPRECATION")
//    setSelectedTabIndicatorHeight(indicatorHeight)
//    tabMode = mTabMode
//    return this
//}
//
//
//
//fun TabLayout.setTitle(position: Int, title: String) {
//    getTabAt(position)?.text = title
//}