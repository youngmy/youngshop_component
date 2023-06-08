package com.young.module.home.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.helper.YLogHelper;
import com.helper.YToastHelper;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;
import com.young.base.activity.BaseActivity;
import com.young.module.home.R;
import com.young.module.home.adapter.MusicListAdapter;
import com.young.module.home.bean.SongBean;
import com.young.module.home.databinding.ActivityLocalMusicBinding;
import com.young.module.home.utils.Constant;
import com.young.module.home.utils.MusicUtils;
import com.young.utils.BLog;
import com.young.utils.MVUtils;
import com.young.utils.MyStatusBarUtil;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalMusicActivity extends
        BaseActivity<ActivityLocalMusicBinding> implements MediaPlayer.OnCompletionListener {

    private static final String TAG = "LocalMusicActivity";

//    private Toolbar toolbar;
    /**
     * 歌曲列表
     */
//    private RecyclerView musicRecyclerView;
    /**
     * 扫描歌曲布局
     */
//    private LinearLayout scanMusicLayout;
    /**
     * 歌曲适配器
     */
    private MusicListAdapter mAdapter;
    /**
     * 歌曲列表
     */
    private List<SongBean> dataList = new ArrayList<>();

    /**
     * 上一次点击的位置
     */
    private int oldPosition = -1;

    /**
     * 底部logo图标，点击之后弹出当前播放歌曲详情页
     */
//    private ShapeableImageView ivLogo;
    /**
     * 底部当前播放歌名
     */
//    private MaterialTextView tvSongName;
    /**
     * 底部当前歌曲控制按钮, 播放和暂停
     */
//    private MaterialButton btnPlay;
    /**
     * 音频播放器
     */
    private MediaPlayer mediaPlayer;
    /**
     * 记录当前播放歌曲的位置
     */
    public int mCurrentPosition = -1;

    /**
     * 自定义进度条
     */
//    private MusicRoundProgressView musicProgress;

    /**
     * 音乐进度间隔时间
     */
    private static final int INTERNAL_TIME = 1000;

    /**
     * 图片动画
     */
    private ObjectAnimator logoAnimation;

    /**
     * 本地音乐数据  不是缓存
     */
    private boolean localMusicData = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_local_music;
    }

    @Override
    public void initView() {
        super.initView();

        mBinding.topLineView.setPadding(0, MyStatusBarUtil.INSTANCE.getHeight(), 0, 0);

//        ActivityLocalMusicBinding binding = DataBindingUtil.setContentView(context, R.layout.activity_local_music);
//        toolbar = mBinding.toolbar;
//        rvMusic = mBinding.rvMusic;
//        layScanMusic = mBinding.layScanMusic;
//        Back(toolbar);

        //当进入页面时发现有缓存数据时，则隐藏扫描布局，直接获取本地数据。
        YLogHelper.INSTANCE.i("LocalMusicActivity===", "===LOCAL_MUSIC_DATA===" + MVUtils.getBoolean(Constant.LOCAL_MUSIC_DATA, false));
        if (MVUtils.getBoolean(Constant.LOCAL_MUSIC_DATA, false)) {
            //省去一个点击扫描的步骤
            mBinding.scanMusicLayout.setVisibility(View.GONE);
            permissionsRequest();
        }
        mBinding.musicRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //暂停
                    showLocationMusic(false);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //滑动
                    showLocationMusic(true);
                }
            }
        });

        initAnimation();
    }

    @Override
    public void initData() {

    }

    /**
     * 页面点击事件
     *
     * @param view 控件
     */
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.scanLocalMusic:
//                //扫描本地音乐
//                permissionsRequest();
//                break;
//            case R.id.locationPlayMusic:
//                //定位当前播放歌曲
//                LinearLayoutManager linearLayoutManager =
//                        (LinearLayoutManager) mBinding.musicRecyclerView.getLayoutManager();
//                linearLayoutManager.scrollToPositionWithOffset(oldPosition, 0);
//                break;
//            default:
//                break;
//        }
        if (view.getId() == R.id.scanLocalMusic) {
            //扫描本地音乐
            permissionsRequest();
        } else if (view.getId() == R.id.locationPlayMusic) {
            //定位当前播放歌曲
            LinearLayoutManager linearLayoutManager =
                    (LinearLayoutManager) mBinding.musicRecyclerView.getLayoutManager();
            linearLayoutManager.scrollToPositionWithOffset(oldPosition, 0);
        } else if (view.getId() == R.id.btn_play) {
            //控制音乐 播放和暂停
            if (mediaPlayer == null) {
                if (dataList.size() == 0) {
                    YToastHelper.INSTANCE.longToast(mBaseActivity(), "没有可播放的音乐");
                    return;
                }
                //没有播放过音乐 ,点击之后播放第一首
                oldPosition = 0;
                mCurrentPosition = 0;
                dataList.get(mCurrentPosition).setCheck(true);
                mAdapter.changeState();
                changeSong(mCurrentPosition);
            } else {
                //播放过音乐  暂停或者播放
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mBinding.btnPlay.setIcon(getDrawable(R.mipmap.icon_play));
                    mBinding.btnPlay.setIconTint(getColorStateList(R.color.white));
                    logoAnimation.pause();
                } else {
                    mediaPlayer.start();
                    mBinding.btnPlay.setIcon(getDrawable(R.mipmap.icon_pause));
                    mBinding.btnPlay.setIconTint(getColorStateList(R.color.gold_color));
                    logoAnimation.resume();
                }
            }
        }
    }


    /**
     * 显示定位当前音乐图标
     */
    private void showLocationMusic(boolean isScroll) {
        //先判断是否存在播放音乐
        if (oldPosition != -1) {
            if (isScroll) {
                //滑动
                mBinding.locationPlayMusic.setVisibility(View.VISIBLE);
            } else {
                //延时隐藏  Android 11（即API 30:Android R）弃用了Handler默认的无参构造方法,所以传入了Looper.myLooper()
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.locationPlayMusic.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        }
    }

    /**
     * 动态权限请求
     */
    private void permissionsRequest() {
        YLogHelper.INSTANCE.i("LocalMusicActivity===", "===permissionsRequest===");
        PermissionX.init(this).permissions(
                        //写入文件
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                        scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .setDialogTintColor(com.young.module.libbase.R.color.base_color_f, R.color.goodMusicMainColor)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        YLogHelper.INSTANCE.i("LocalMusicActivity===", "===allGranted===" + allGranted);
                        if (allGranted) {
                            //通过后的业务逻辑
                            //获取音乐列表
                            getMusicList();
                        } else {
                            YToastHelper.INSTANCE.longToast(mBaseActivity(), "您拒绝了如下权限：" + deniedList);
                        }
                    }
                });
    }

    /**
     * 获取音乐列表
     */
    private void getMusicList() {
        localMusicData = MVUtils.getBoolean(Constant.LOCAL_MUSIC_DB, false);
        //清除列表数据
        dataList.clear();
        if (localMusicData) {
            //有数据则读取本地数据库的数据
            BLog.d(TAG, "读取本地数据库 ====>");
            dataList = LitePal.findAll(SongBean.class);
        } else {
            //没有数据则扫描本地文件夹获取音乐数据
            BLog.d(TAG, "扫描本地文件夹 ====>");
            dataList = MusicUtils.getMusicData(this);
        }
        if (dataList != null && dataList.size() > 0) {
//            //是否有缓存歌曲
//            MVUtils.put(Constant.LOCAL_MUSIC_DATA, true);
//            mBinding.scanMusicLayout.setVisibility(View.GONE);
            //显示本地音乐
            showLocalMusicData();
            if (!localMusicData) {
                //添加到本地数据库中
                addLocalDB();
            }
        } else {
            YToastHelper.INSTANCE.longToast(mBaseActivity(), "暂无数据");
        }

    }

    /**
     * 显示本地音乐数据
     */
    private void showLocalMusicData() {
        //指定适配器的布局和数据源
        mAdapter = new MusicListAdapter(mBaseActivity(), R.layout.item_music_list, dataList);
        //线性布局管理器，可以设置横向还是纵向，RecyclerView默认是纵向的，所以不用处理,如果不需要设置方向，代码还可以更加的精简如下
        mBinding.musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mBinding.musicRecyclerView.setAdapter(mAdapter);

        mAdapter.addChildClickViewIds(R.id.item_music);
        //item的点击事件
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_music) {

                    //控制当前播放位置
                    playPositionControl(position);

                    mCurrentPosition = position;
                    changeSong(mCurrentPosition);
                }
            }
        });

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
    }

    /**
     * 控制播放位置
     *
     * @param position
     */
    private void playPositionControl(int position) {
        if (oldPosition == -1) {
            //未点击过 第一次点击
            oldPosition = position;
            dataList.get(position).setCheck(true);
        } else {
            //大于 1次
            if (oldPosition != position) {
                dataList.get(oldPosition).setCheck(false);
                dataList.get(position).setCheck(true);
                //重新设置位置，当下一次点击时position又会和oldPosition不一样
                oldPosition = position;
            }
        }
        mAdapter.changeState();
    }

    /**
     * 切换歌曲
     */
    private void changeSong(int position) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            //监听音乐播放完毕事件，自动下一曲
            mediaPlayer.setOnCompletionListener(this);
        }

        try {
            //切歌前先重置，释放掉之前的资源
            mediaPlayer.reset();
            BLog.i(TAG, dataList.get(position).path);
            //设置播放音频的资源路径
            mediaPlayer.setDataSource(dataList.get(position).path);
            //设置歌曲所在专辑的封面图片
            mBinding.ivLogo.setImageBitmap(MusicUtils.getAlbumPicture(mBaseActivity(), dataList.get(position).getPath()));
            //设置播放的歌名和歌手
            mBinding.tvSongName.setText(dataList.get(position).song + " - " + dataList.get(position).singer);
            //如果内容超过控件，则启用跑马灯效果
            mBinding.tvSongName.setSelected(true);
            //开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();
            //开始播放音频
            mediaPlayer.start();

            mBinding.musicProgress.setProgress(0, mediaPlayer.getDuration());
            //更新进度
            updateProgress();

            //播放按钮控制
            if (mediaPlayer.isPlaying()) {
                mBinding.btnPlay.setIcon(getDrawable(R.mipmap.icon_pause));
                mBinding.btnPlay.setIconTint(getColorStateList(R.color.gold_color));
                logoAnimation.resume();
            } else {
                mBinding.btnPlay.setIcon(getDrawable(R.mipmap.icon_play));
                mBinding.btnPlay.setIconTint(getColorStateList(R.color.white));
                logoAnimation.pause();
            }

            //图片旋转动画
            logoAnimation.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 播放完成之后自动下一曲
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

        int position = -1;
        if (dataList != null) {
            if (mCurrentPosition == dataList.size() - 1) {
                //当前为最后一首歌时,则切换到列表的第一首歌
                position = mCurrentPosition = 0;
            } else {
                position = ++mCurrentPosition;
            }
        }

        //移动播放位置
        playPositionControl(position);
        //切歌
        changeSong(position);

    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            // 展示给进度条和当前时间
            int progress = mediaPlayer.getCurrentPosition();
            mBinding.musicProgress.setProgress(progress, mediaPlayer.getDuration());

            //更新进度
            updateProgress();
            return true;
        }
    });

    /**
     * 更新进度
     */
    private void updateProgress() {
        // 使用Handler每间隔1s发送一次空消息，通知进度条更新
        // 获取一个现成的消息
        Message msg = Message.obtain();

        // 使用MediaPlayer获取当前播放时间除以总时间的进度
        int progress = mediaPlayer.getCurrentPosition();
        msg.arg1 = progress;
        mHandler.sendMessageDelayed(msg, INTERNAL_TIME);
    }


    /**
     * 初始化动画
     */
    private void initAnimation() {
        logoAnimation = ObjectAnimator.ofFloat(mBinding.ivLogo, "rotation", 0.0f, 360.0f);
        logoAnimation.setDuration(6000);
        logoAnimation.setInterpolator(new LinearInterpolator());
        logoAnimation.setRepeatCount(-1);
        logoAnimation.setRepeatMode(ObjectAnimator.RESTART);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mediaPlayer) {
            mediaPlayer.stop();
        }
    }

    /**
     * 添加到本地数据库
     */
    private void addLocalDB() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < dataList.size(); i++) {
                    SongBean song = new SongBean();
                    song.setSinger(dataList.get(i).getSinger());
                    song.setSong(dataList.get(i).getSong());
                    song.setAlbumId(dataList.get(i).getAlbumId());
                    song.setAlbum(dataList.get(i).getAlbum());
                    song.setPath(dataList.get(i).getPath());
                    song.setDuration(dataList.get(i).getDuration());
                    song.setSize(dataList.get(i).getSize());
                    song.setCheck(dataList.get(i).isCheck());
                    song.save();
                }
                List<SongBean> list = LitePal.findAll(SongBean.class);
                if (list.size() > 0) {
                    MVUtils.put(Constant.LOCAL_MUSIC_DB, true);
                    BLog.d(TAG, "添加到本地数据库的音乐：" + list.size() + "首");
                }

            }
        });

    }
}
