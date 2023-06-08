package com.young.network;

import com.young.network.utils.MD5Helper;

import java.util.HashMap;
import java.util.Map;

public class ParamsHelper {

    public static Map<String, String> getParams(){
        Map<String, String> map  = new HashMap<>();
        map.put("appid","1000");
        String time = System.currentTimeMillis() / 1000 + "";
        map.put("timeline",time);
        map.put("packver","1000");
        map.put("token","");
        map.put("os","0");
        map.put("screen","");
        map.put("device","");
        map.put("network","");
        map.put("sign", MD5Helper.getSign("8801eedd308f5e60ff471eb63bce9ffe", map));
        return map;
    }

}
