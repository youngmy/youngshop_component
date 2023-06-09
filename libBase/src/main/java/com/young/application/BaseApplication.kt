package com.young.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.helper.YLogHelper
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.young.module.libbase.R
import com.young.utils.ActivityManager
import com.young.utils.MVUtils
import me.jessyan.autosize.AutoSizeConfig
import org.litepal.LitePal

open class BaseApplication : Application() {

    companion object {

        lateinit var activityManager: ActivityManager

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var instance: BaseApplication

        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.base_color_F5, R.color.base_color_F5) //全局设置主题颜色
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        //声明Activity管理
        activityManager = ActivityManager()
        context = applicationContext
        instance = this
        //MMKV初始化
        val initialize = MMKV.initialize(this)
        YLogHelper.i("BaseApplication===", "MMKV INIT $initialize")
        //工具类初始化
        MVUtils.getInstance()
        LitePal.initialize(this)
        AutoSizeConfig.getInstance().setCustomFragment(true);
    }

    open fun getActivityManager(): ActivityManager? {
        return activityManager
    }

    /**
     * 内容提供器
     * @return
     */
    open fun getContext(): Context? {
        return context
    }

    open fun getApplication(): BaseApplication? {
        return instance
    }

}