package com.young.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.young.http.HttpResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static String readLocalJson(Context context, String jsonName) {
        String jsonString;
        StringBuilder resultString = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    context.getResources().getAssets().open(jsonName + ".json")));
            while ((jsonString = bufferedReader.readLine()) != null) {
                resultString.append(jsonString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString.toString();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        Object object = gson.fromJson(json, classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    public static <T> List<T> fromJsonList(String json) {
        List<T> list = new ArrayList<>();
        if (json == null) {
            return list;
        }
        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <T> HttpResult<T> getResult(String result, Class<T> classOfT) {
        HttpResult httpResult ;
        if (TextUtils.isEmpty(result)){
            httpResult=new HttpResult();
            httpResult.text="";
            httpResult.code=201;
            httpResult.msg="内容为空";
        }else {
            httpResult = new Gson().fromJson(result, HttpResult.class);
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (result.contains("data")) {
                    httpResult.text=jsonObject.getString("data");
                    if (jsonObject.getString("data").startsWith("[") && jsonObject.getString("data").endsWith("]")) {
                        if (!jsonObject.getString("data").equals("[]")) {
                            List<Object> list = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    list.add(new Gson().fromJson(jsonArray.getString(i), classOfT));
                                } catch (Exception e) {
                                    list.add(null);
                                }
                            }
                            httpResult.data=list;
                        } else {
                            httpResult.data= new ArrayList<>();
                        }
                    } else {
                        try {
                            httpResult.data=new Gson().fromJson(httpResult.text, classOfT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                   //
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return httpResult;
    }

}
