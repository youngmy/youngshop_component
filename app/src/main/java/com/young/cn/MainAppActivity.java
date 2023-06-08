package com.young.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.young.cn.api.ApiService;
import com.young.cn.application.MyApplication;
import com.young.cn.db.CustomDisposable;
import com.young.cn.db.UserInfo;
import com.young.network.NetWorkApi;
import com.young.network.observer.BaseObserver;
import com.young.network.utils.KLog;

import android.util.Log;

import java.util.List;

import com.young.utils.MVUtils;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class MainAppActivity extends AppCompatActivity {

    private ImageView imageView;

    //点击6次
    private final int CLICK_NUM = 6;
    //点击时间间隔5秒
    private final int CLICK_INTERVAL_TIME = 5000;
    //上一次的点击时间
    private long lastClickTime = 0;
    //记录点击次数
    private int clickNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, String> map  = ParamsHelper.getParams();
//                map.put("account","13618045641");
//                map.put("password","123456");
//                NetWorkApi.createService(ApiService.class,0)
//                        .requestLogin(map)
//                        .compose(NetWorkApi.applySchedulers(new BaseObserver<ConfigBean>() {
//                            @Override
//                            public void onSuccess(ConfigBean s) {
//                                KLog.e("MainAppActivity", "===s==="+s.errcode);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable e) {
//                                KLog.e("MainAppActivity", "===="+e.toString());
//                            }
//                        }));

                Completable insert = MyApplication.Companion.getDb().getUserInfoDao().insertAll(
                        new UserInfo(1, "张三", 30)
                );
                CustomDisposable.addDisposable(insert, ()->{

                    //从数据库获取
                    Flowable<UserInfo> userInfoFlowable = MyApplication.Companion.getDb().getUserInfoDao().queryById(1);
                    //RxJava处理Room数据获取
//                    CustomDisposable.addDisposable(userInfoFlowable, userInfo -> {
//
//                        KLog.e("MainAppActivity", "==getUserName=="+userInfo.getUserName());
//                    });
                    CustomDisposable.addDisposable(userInfoFlowable, new Consumer<UserInfo>() {
                        @Override
                        public void accept(UserInfo userInfo) throws Exception {

                        }
                    });
                });



            }
        });
        //访问网络
        requestNetwork();

        //存
        Log.d("TAG", "onCreate: 存");
        MVUtils.put("age", 24);
        //取
        int age = MVUtils.getInt("age", 0);
        Log.d("TAG", "onCreate: 取 ：" + age);

    }

    /**
     * 访问网络
     */
    @SuppressLint("CheckResult")
    private void requestNetwork() {
        NetWorkApi.createService(ApiService.class, 1)
                .getWallPaper()
                .compose(NetWorkApi.applySchedulers(new BaseObserver<WallPaperResponse>() {
                    @Override
                    public void onSuccess(WallPaperResponse wallPaperResponse) {
                        List<WallPaperResponse.ResBean.VerticalBean> vertical = wallPaperResponse.getRes().getVertical();
                        if (vertical != null && vertical.size() > 0) {
                            String imgUrl = vertical.get(0).getImg();
                            Glide.with(MainAppActivity.this).load(imgUrl).into(imageView);
                        } else {
                            Toast.makeText(MainAppActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        KLog.e("MainAppActivity", e.toString());
                        Toast.makeText(MainAppActivity.this, "访问失败", Toast.LENGTH_SHORT).show();
                    }
                }));
    }


}

