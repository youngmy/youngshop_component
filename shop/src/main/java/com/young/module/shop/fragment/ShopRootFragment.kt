package com.young.module.shop.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.shopRootFragment
import com.young.module.shop.R
import com.young.module.shop.databinding.ActivityShopBinding

@Route(path = shopRootFragment)
class ShopRootFragment : BaseFragment<ActivityShopBinding>(R.layout.activity_shop) {


    override fun initData() {

    }
}