package com.young.cn.api;
import com.young.cn.WallPaperResponse;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.Map;
import com.young.cn.ConfigBean;

/**
 * ApiService接口 统一管理应用所有的接口
 * @author llw
 */
public interface ApiService {

    /**
     * 获取热门壁纸列表
     */
    @GET("/v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Observable<WallPaperResponse> getWallPaper();


    @POST("api/app/config")
    @FormUrlEncoded
    Observable<ConfigBean> requestConfig(@FieldMap Map<String,String> params);

    @POST("api/user/login")
    @FormUrlEncoded
    Observable<ConfigBean> requestLogin(@FieldMap Map<String,String> params);
}

