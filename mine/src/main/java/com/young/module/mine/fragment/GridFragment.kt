package com.young.module.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import com.young.base.fragment.BaseFragment
import com.young.module.mine.adapter.FiveGridAdapter
import com.young.module.mine.bean.MenuBean
import com.young.module.mine.databinding.FiveMenuGridviewBinding

class GridFragment(data: MutableList<MenuBean>, index: Int, pageSize: Int): Fragment() {

    private val nineGridAdapter: FiveGridAdapter by lazy { FiveGridAdapter(this.requireContext(), data, index, pageSize) }

//    override fun initView() {
////        mBinding.gridView.run {
////            adapter = nineGridAdapter as ListAdapter
////        }
//    }
//
//    override fun initData() { }


    private lateinit var binding:FiveMenuGridviewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FiveMenuGridviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gridView.run {
            adapter = nineGridAdapter as ListAdapter
        }
    }

}