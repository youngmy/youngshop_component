package com.young.ext


import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager


fun ViewPager.initMyViewPager(mAdapter: PagerAdapter?,
                              getCurrentItem: Int = 0,
                              getOffscreenPageLimit: Int = 1): ViewPager {
    offscreenPageLimit = getOffscreenPageLimit
    adapter = mAdapter
    currentItem = getCurrentItem
    return this
}