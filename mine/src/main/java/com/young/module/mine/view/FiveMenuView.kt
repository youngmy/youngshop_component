package com.young.module.mine.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.helper.YLogHelper
import com.young.module.mine.adapter.FiveViewPagerAdapter
import com.young.module.mine.bean.MenuBean
import com.young.module.mine.databinding.LayoutFiveMenuMainBinding
import kotlin.math.ceil

class FiveMenuView(var content: Context, fragment: Fragment): FrameLayout(content) {

    private var binding: LayoutFiveMenuMainBinding =
        LayoutFiveMenuMainBinding.inflate(LayoutInflater.from(context), this, true)

    private val pageSize: Int = 5
    private var points: ArrayList<ImageView> = arrayListOf()
    private var menuList: ArrayList<MenuBean> = arrayListOf()
    private var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams(16, 16))

    private val fiveViewPagerAdapter: FiveViewPagerAdapter by lazy { FiveViewPagerAdapter(fragment, menuList, pageSize) }

    init {
        binding.fiveViewPager.adapter = fiveViewPagerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MenuBean>) {
        val totalPage = ceil(data.size * 1.0 / pageSize).toInt()
        YLogHelper.i("FiveMenuView===","===totalPage===$totalPage")
        binding.fiveMemuLayout.visibility = View.VISIBLE
        menuList.clear()
        menuList.addAll(data)
        fiveViewPagerAdapter.notifyDataSetChanged()

        binding.fiveViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedPoints(position + 1)
            }
        })

        layoutParams.leftMargin = 5
        layoutParams.rightMargin = 5

        if (points.size != totalPage) {
            points.clear()
            for (current in 1..totalPage) {
                val imageView = ImageView(content)
                imageView.layoutParams = ViewGroup.LayoutParams(16,16)
                if(current == 1){
                    imageView.setBackgroundResource(com.young.module.libbase.R.drawable.selected_indicator)
                }else{
                    imageView.setBackgroundResource(com.young.module.libbase.R.drawable.normal_indicator)
                }
                points.add(imageView)
                binding.pointsLayout.addView(imageView, layoutParams)
            }
        }
    }


    private fun selectedPoints(position: Int) {
        for(index in 1..points.size) {
            if (index == position) {
                points[index-1].setBackgroundResource(com.young.module.libbase.R.drawable.selected_indicator)
            } else {
                points[index-1].setBackgroundResource(com.young.module.libbase.R.drawable.normal_indicator)
            }
        }
    }
}