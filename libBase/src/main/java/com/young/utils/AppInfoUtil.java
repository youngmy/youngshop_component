package com.young.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.young.application.BaseApplication;


/**
 * Created by Roy on 2015/4/14.
 */
public class AppInfoUtil {
    private static AppInfoUtil instance;
    private Context mContext;
    private String packageName;
    private int versionCode = 0;
    private String versionName = null;
    private String channel = null;

    private String host_url = null;



    public static AppInfoUtil getInstance(){
        if(instance == null){
            instance = new AppInfoUtil(BaseApplication.Companion.getContext());
        }


        return instance;
    }
    /**
     *
     */
    public AppInfoUtil(Context context) {
        mContext = context;

        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionCode = packageInfo.versionCode; // 版本号
            versionName = packageInfo.versionName; // 版本名
            packageName = packageInfo.packageName;
            ApplicationInfo applicationInfo = pm.getApplicationInfo(mContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            channel = bundle.getString("JM_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return the versionCode
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    public String getPackageName() {
        return packageName;
    }







}
