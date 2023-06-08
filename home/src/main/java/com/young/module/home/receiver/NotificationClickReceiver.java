package com.young.module.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.young.utils.BLog;

public class NotificationClickReceiver extends BroadcastReceiver {

    public static final String TAG = "NotificationClickReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        BLog.d(TAG,"通知栏点击");

    }

}
