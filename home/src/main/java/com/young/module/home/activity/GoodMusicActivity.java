package com.young.module.home.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;


import androidx.core.app.NotificationCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.helper.YToastHelper;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.young.base.activity.BaseActivity;
import com.young.module.home.R;
import com.young.module.home.bean.SongBean;
import com.young.module.home.databinding.ActivityGoodMusicBinding;
import com.young.module.home.service.MusicService;
import com.young.utils.BLog;
import com.young.utils.MyStatusBarUtil;

import org.litepal.LitePal;

import java.util.List;

@Route(path = com.young.commonconfig.helper.ARouterHelperKt.goodMusicActivity)
public class GoodMusicActivity extends BaseActivity<ActivityGoodMusicBinding> {

    private static final String TAG = "GoodMusicActivity";

    private List<SongBean> mList;

    private MusicService.MusicBinder musicBinder;

    private MusicService musicService;



    @Override
    public int getLayoutId() {
        return R.layout.activity_good_music;
    }

    @Override
    public void initData() {
        BLog.d(TAG, "initData");

        mBinding.topLineView.setPadding(0, MyStatusBarUtil.INSTANCE.getHeight(), 0, 0);

        //绑定服务
        Intent serviceIntent = new Intent(mBaseActivity(), MusicService.class);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);

    }

    public void onClick(View view) {
        startActivity(new Intent(mBaseActivity(), LocalMusicActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBar
                .with(this)
                .statusBarView(mBinding.topLineView)
                .transparentStatusBar()//透明状态栏，不写默认透明色
                .statusBarDarkFont(true)//状态栏字体是深色，不写默认为亮色
                .init();
        BLog.d(TAG, "onResume");
        mList = LitePal.findAll(SongBean.class);
        mBinding.tvLocalMusicNum.setText(String.valueOf(mList.size()));
        //初始化ViewPager2，赋值，剩下的交给卧底去处理播放信息
//        initViewPager2();
    }

    /**
     * 服务连接
     */
    private ServiceConnection connection = new ServiceConnection() {

        /**
         * 连接服务
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicService.MusicBinder) service;
            musicService = musicBinder.getService();
            BLog.d(TAG, "Service与Activity已连接");

        }

        //断开服务
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBinder = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }





}
