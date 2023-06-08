package com.young.module.mine.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.young.module.mine.bean.MenuBean
import com.young.module.mine.fragment.GridFragment
import kotlin.math.ceil

class FiveViewPagerAdapter(fragment: Fragment, var data: List<MenuBean>, var pageSize: Int): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return ceil(data.size * 1.0 / pageSize).toInt()
    }

    override fun createFragment(position: Int): Fragment {
        return GridFragment(data.toMutableList(), position, pageSize)
    }
}