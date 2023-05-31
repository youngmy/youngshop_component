package com.young.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class FragmentViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    mFragments: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    private var mFragments = ArrayList<Fragment>()

    init {
        this.mFragments.clear()
        this.mFragments = mFragments
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

}