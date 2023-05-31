package com.young.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.young.application.BaseApplication;


public class PrefersUtil {

    private final static String DATA = "data";
    private static PrefersUtil PREFERSUTIL;
    private static SharedPreferences cache;
    private SharedPreferences.Editor editor;

    private PrefersUtil(Context fra_act) {
        cache = fra_act.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        editor = cache.edit();
    }

    public static PrefersUtil getSingle() {
        if (PREFERSUTIL == null) {
            PREFERSUTIL = new PrefersUtil(BaseApplication.Companion.getContext());
        }
        return PREFERSUTIL;
    }

    public static void removeSpf(Context con, String key) {
        cache = con.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        cache.edit().remove(key).commit();
    }

    public String getStrValue(String key) {
        return cache.getString(key, null);
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void removeKey(String key) {
        editor.remove(key).commit();
    }

    public boolean isExistKey(String key) {
        return cache.contains(key);
    }

    public int getIntValue(String key, int defaultInt) {
        return cache.getInt(key, defaultInt);
    }

    public void setValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public Boolean getBooleanValue(String key, Boolean defaultBoolean) {
        return cache.getBoolean(key, defaultBoolean);
    }

    public void setValue(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setValue(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLongValue(String key, long defaultLong) {
        return cache.getLong(key, defaultLong);
    }

}
