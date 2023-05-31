package com.young.module.mine.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.fragment.BaseFragment
import com.young.commonconfig.helper.mineRootFragment
import com.young.module.mine.R
import com.young.module.mine.Test
import com.young.module.mine.databinding.ActivityMineBinding
import com.young.utils.Message
import com.young.utils.WeChatHelper

@Route(path = mineRootFragment)
class MineRootFragment : BaseFragment<ActivityMineBinding>(R.layout.activity_mine) {


    override fun initData() {
        mBinding.shareWeChat.setOnClickListener {
//    WeChatHelper.shareTextToWeChat(mBaseActivity(),"微信")
            Test.test(mBaseActivity())
        }
        mBinding.shareWeChatFriend.setOnClickListener {
//            WeChatHelper.shareTextToWeChatFriend(mBaseActivity(), Message(
//                title = "标题",
//
//            ))
        }
    }

}