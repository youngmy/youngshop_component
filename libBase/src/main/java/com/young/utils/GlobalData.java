package com.young.utils;


public class GlobalData {

//    public DeviceUtil deviceData;
    public ScreenUtility screenData;
    public AppInfoUtil appInfoUtil;

    public static GlobalData getInstance() {
        return ContentInstance.instance;
    }

    private GlobalData() {
//        deviceData = DeviceUtil.getInstance();
        screenData = ScreenUtility.getInstance();
        appInfoUtil = AppInfoUtil.getInstance();
    }

    private static final class ContentInstance {
        public static GlobalData instance = new GlobalData();
    }

}
