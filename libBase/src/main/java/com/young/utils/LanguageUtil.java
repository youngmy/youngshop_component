package com.young.utils;
 
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
 
import java.util.Locale;
 
public class LanguageUtil {
 
    /**
     * 切换语言 sdk N以下
     * @param context
     * @param newLanguage
     */
    public static void changeAppLanguage(Context context, Locale newLanguage) {
        if (TextUtils.isEmpty(newLanguage.getLanguage())) {
            return;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            //大于等于sdk17配置
//            configuration.setLocale(newLanguage);
//        } else {
//            //小于sdk17配置
//            configuration.locale = newLanguage;
//        }
        //大于等于sdk17配置
        configuration.setLocale(newLanguage);
        // updateConfiguration
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }
 
    /**
     * android N 以上切换语言适配
     * @param context   上下文
     * @param newLanguage   语言代码
     * @return 返回上下文
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static Context createResources(Context context, String newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getLocaleByLanguage(newLanguage);
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        //创建配置
        return context.createConfigurationContext(configuration);
    }
 
    /**
     * 语言代码转Locale
     * @param newLanguage 语言代码
     * @return
     */
    private static Locale getLocaleByLanguage(String newLanguage) {
        if (newLanguage.equals("en")){
            return  Locale.ENGLISH;
        } else if (newLanguage.equals("ja")){
            return Locale.JAPAN;
        } else {
            return Locale.SIMPLIFIED_CHINESE;
        }
    }
 
}