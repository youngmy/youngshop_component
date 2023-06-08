package com.young.base.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.young.application.BaseApplication
import java.lang.reflect.Field

abstract class BaseActivity<VB : ViewDataBinding>(@LayoutRes layoutId: Int = 0) :
    AppCompatActivity(layoutId) {

    fun mBaseActivity() = mBaseActivity!!
    private var mBaseActivity: AppCompatActivity? = null

//    private var mActionBarView: ActionBarView? = null
    private var mLayoutError: LinearLayout? = null
    private var mLayoutId = layoutId
    lateinit var mBinding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        mBaseActivity = this
        //添加继承这个BaseActivity的Activity
        BaseApplication.activityManager.addActivity(this)
        try {
            //默认状态栏为白底黑字
//            darkMode(BaseConfig.statusBarDarkMode)
//            statusBarColor(ContextCompat.getColor(this, BaseConfig.statusBarColor))
//            setContentView(R.layout.activity_base)
//            val baseChild = findViewById<FrameLayout>(R.id.layout_base_child)
//            mLayoutError = findViewById(R.id.layout_empty_or_error)
//            mActionBarView = findViewById(R.id.action_bar)
            if (mLayoutId == 0) {
                mLayoutId = getLayoutId()
            }


            if (savedInstanceState != null && getIntercept()) {
                noEmptyBundle()
                return
            }

            Log.i("============","=========StartActivity====mLayoutId====$mLayoutId")
//            val childView = layoutInflater.inflate(mLayoutId, null)
//            baseChild.addView(childView)
            mBinding = DataBindingUtil.setContentView(this,mLayoutId)


            initView()
            initData()
        } catch (e: Exception) {
            e.printStackTrace()
            noEmptyBundle()
        }
    }


    /**
     * 获取视图id
     */
    open fun getLayoutId(): Int {
        return 0
    }


    open fun initView() {}


    /**
     * 初始化数据
     */
    abstract fun initData()




    /**
     * 动态改变状态栏颜色和标题
     */
//    fun setDarkTitle(dark: Boolean, color: Int, title: String) {
//        try {
//            darkMode(dark)
//            statusBarColor(ContextCompat.getColor(this, color))
//            setBarTitle(title)
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//
//    }


    /**
     *设置标题
     */
    fun setBarTitle(title: String) {
//        mActionBarView!!.visibility = View.VISIBLE
//        mActionBarView!!.setBarTitle(title)
    }




    /**
     * 隐藏左侧按钮
     */


    fun hintLeftMenu() {
//        mActionBarView!!.hintLeftBack()
    }




    /**
     * 获取ActionBarView
     */
//    fun getActionBarView(): ActionBarView {
//        return mActionBarView!!
//    }


    /**
     * 隐藏标题栏
     */
//    fun hintActionBar() {
//        mActionBarView?.visibility = View.GONE
//    }


    /**
     * Bundle为空进行拦截，解决改变权限后重回App崩溃问题
     */
    open fun getIntercept(): Boolean {
        return false
    }


    /**
     * Bundle为空时的逻辑处理，解决改变权限后重回App崩溃问题
     */
    open fun noEmptyBundle() {}




    override fun onDestroy() {
        super.onDestroy()
        try {
//            LiveDataBus.removeObserve(this)
//            LiveDataBus.removeStickyObserver(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




    /**
     * 透明状态栏
     */
    fun translucentWindow(dark: Boolean) {
//        try {
//            immersive(0, dark)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }


    }


    /**
     * 设置缺省页
     */
    fun setEmptyOrError(view: View) {
        mLayoutError?.visibility = View.VISIBLE
        mLayoutError?.removeAllViews()
        mLayoutError?.addView(view)
    }


    /**
     * 隐藏
     */
    fun hintEmptyOrErrorView() {
        mLayoutError?.visibility = View.GONE
    }


    /**
     * 获取错误或为空的view
     */
    fun getEmptyOrErrorView(): LinearLayout {
        return mLayoutError!!
    }

    protected fun loadRootFragment(@IdRes containerId: Int, @NonNull toFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, toFragment)
        transaction.commit()
    }

    @SuppressLint("PrivateApi")
    private fun setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                val field: Field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.setInt(window.decorView, Color.TRANSPARENT) //设置透明
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 快速点击的时间间隔
     */
    private val fastClickDelayTime = 500

    /**
     * 最后点击的时间
     */
    private var lastClickTime: Long = 0
    /**
     * 两次点击间隔不能少于500ms  防止多次点击
     *
     * @return flag
     */
     open fun isFastClick(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= fastClickDelayTime) {
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }

}