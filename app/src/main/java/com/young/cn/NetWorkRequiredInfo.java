package com.young.cn;
import android.app.Application;
import com.young.network.INetWorkRequiredInfo;

public class NetWorkRequiredInfo implements INetWorkRequiredInfo {

    private Application application;

    public NetWorkRequiredInfo(Application application) {
        this.application = application;
    }

    /**
     * 版本名
     */
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }
    /**
     * 版本号
     */
    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    /**
     * 是否为debug
     */
    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 应用全局上下文
     */
    @Override
    public Application getApplicationContext() {
        return application;
    }

}
