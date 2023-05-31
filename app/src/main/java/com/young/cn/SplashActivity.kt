package com.young.cn

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.young.base.activity.BaseActivity
import com.young.cn.databinding.ActivityStartBinding
import com.young.commonconfig.helper.*


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivityStartBinding>() {

    override fun getLayoutId(): Int {
//        installSplashScreen()
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()
//        ImmersionBar.with(this).transparentBar().init()
    }

    override fun initData() {
        ARouter.getInstance().build(mainActivity).navigation()
//        Log.i("============","=========StartActivity========")
//        mBinding.textView.setOnClickListener {
//            ARouter.getInstance().build("/module/main/MainActivity").navigation()
////        startActivity(Intent(this, MainActivity::class.java))
//            Log.i("============","=========StartActivity========")
//        }
        Log.i(
            "============",
            "=========StartActivity========${intent.getStringExtra("title")}\n" + "${
                intent.getStringExtra("content")
            }"
        )

        mBinding.demoTextView.setOnClickListener {
            ARouter.getInstance().build(demoActivity).navigation()
//            ARouter.getInstance().build("/module/main/MainActivity").navigation()
        }

        //获取系统通知服务
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //初始化通知
        initNotification()
        //显示通知
        mBinding.btnShow.setOnClickListener {
            notificationManager.notify(notificationId, notification)
        }


    }


    //渠道Id
    private val channelId = "test"

    //渠道名
    private val channelName = "测试通知"

    //渠道重要级
    private val importance = NotificationManagerCompat.IMPORTANCE_HIGH

    //通知管理者
    private lateinit var notificationManager: NotificationManager

    //通知
    private lateinit var notification: Notification

    //通知Id
    private val notificationId = 1

    /**
     * 创建通知渠道
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, importance: Int) =
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                importance
            )
        )


    /**
     * 初始化通知
     */
    private fun initNotification() {
        val title = "打工人"
        val content = "我要搞钱！！！"
        // 为DetailsActivity 创建显式 Intent
        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("title", title).putExtra("content", content)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通知渠道
            createNotificationChannel(channelId, channelName, importance)
            NotificationCompat.Builder(this, channelId)
        } else {
            NotificationCompat.Builder(this)
        }.apply {
            setSmallIcon(R.mipmap.ic_launcher)//小图标（显示在状态栏）
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))//大图标（显示在通知上）
            setContentTitle(title)//标题
            setContentText(content)//内容
            setContentIntent(pendingIntent)//设置内容意图
            setAutoCancel(true)//设置自动取消
        }.build()
    }


}