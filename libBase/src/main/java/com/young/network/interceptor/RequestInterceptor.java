package com.young.network.interceptor;

import com.young.network.INetWorkRequiredInfo;
import com.young.network.ParamsHelper;
import com.young.network.utils.DateUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.young.network.utils.MD5Helper;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private INetWorkRequiredInfo iNetworkRequiredInfo;

    public RequestInterceptor(INetWorkRequiredInfo iNetworkRequiredInfo){
        this.iNetworkRequiredInfo = iNetworkRequiredInfo;
    }

    /**
     * 拦截
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
//        String nowDateTime = DateUtil.getNowDateTime();
        //构建器
        Request.Builder builder = chain.request().newBuilder();
//        //添加使用环境
//        builder.addHeader("os","android");
//        //添加版本号
//        builder.addHeader("appVersionCode",this.iNetworkRequiredInfo.getAppVersionCode());
//        //添加版本名
//        builder.addHeader("appVersionName",this.iNetworkRequiredInfo.getAppVersionName());
//        //添加日期时间
//        builder.addHeader("datetime",nowDateTime);
        //8801eedd308f5e60ff471eb63bce9ffe
//        Map<String, String> map  = ParamsHelper.getParams();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            builder.addHeader(entry.getKey(),entry.getValue());
//        }

        //返回
        return chain.proceed(builder.build());
    }
}

