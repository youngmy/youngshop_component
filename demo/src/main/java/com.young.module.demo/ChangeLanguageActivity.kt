package com.young.module.demo

import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.young.base.activity.BaseActivity
import com.young.commonconfig.helper.changeLanguageActivity
import com.young.module.demo.databinding.ActivityChangeLanguageBinding
import com.young.utils.LanguageUtil
import com.young.utils.PrefersUtil
import java.util.Locale

@Route(path = changeLanguageActivity)
class ChangeLanguageActivity  : BaseActivity<ActivityChangeLanguageBinding>(R.layout.activity_change_language) {

    /**
     * https://blog.csdn.net/baozouge_0606/article/details/121803795
     * 第一步：生成相对应语言的string文件，如下图，选中点击res-->new-->Android Resourse Directory，进入Locale配置界面
     * 第二步：选中Locale，点击向右双箭头
     * 第三步：选择自己要配置的系统语言，按下图选择语言和语言的种类
     * 第四步：选择好语言后，系统会自动生成相对应的values文件，只需要在指定的文件创建strings的xml文件就好，做好这一步，app就可以跟随手机系统语言变化而自动切换语言了。
     * 第五步：做手动切换系统语言操作，先分享一下工具类 LanguageUtil
     * 第七步：activity调用以及手动切换
     * 第八步：activity中对button设置点击事件，来切换语言
     * 第九步：在Activity中重写attachBaseContext()方法，用于在界面创建之前修改context参数，此方法最好写在BaseActivity中，代码如下：
     */

    override fun initData() {
        mBinding.aBtn.setOnClickListener {
            setLanguage(Locale.SIMPLIFIED_CHINESE)
        }
        mBinding.bBtn.setOnClickListener {
            setLanguage(Locale.ENGLISH)
        }
        mBinding.cBtn.setOnClickListener {
            setLanguage(Locale.JAPAN)
        }
    }

    private fun setLanguage(language: Locale?) {
        //全局保存语言代码
//        new SpUtil(this,"LANGUAGE").putValue("LANGUAGE",language.getLanguage());
        PrefersUtil.getSingle().setValue("LANGUAGE", language?.language)
        //切换语言
        updateLanguage(language)
    }

    /**
     * 更新语言
     * @param newLanguage 要更新的语言
     */
    private fun updateLanguage(newLanguage: Locale?) {
        //不同版本设置方式不一样
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            //小于sdk N
            LanguageUtil.changeAppLanguage(this, newLanguage)
        }
        //重启activity
        recreate()
    }

    override fun attachBaseContext(newBase: Context?) {
        //获取全局的language代码值
//        val language = SpUtil(newBase, "LANGUAGE").getValue("LANGUAGE", "") as String
        val language = if (TextUtils.isEmpty(PrefersUtil.getSingle().getStrValue("LANGUAGE"))) {
            ""
        } else {
            PrefersUtil.getSingle().getStrValue("LANGUAGE")
        }

        //android N及以上需要更新Context
        super.attachBaseContext(LanguageUtil.createResources(newBase, language))
    }


}