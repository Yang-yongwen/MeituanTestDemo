package com.example.yangyongwen.meituantestdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yangyongwen.meituantestdemo.App;

import java.util.Set;

/**
 * Created by samsung on 2016/6/30.
 */
public  class SharedPreferencesUtils {
    
    
    private static final String APP_PREFERENCE="meituan_test_demo_pre";
    

    private static Context context= App.getInstance();  // 实例代码，实际应该以依赖注入

    
    public static void setContext(Context mContext){
        context=mContext;
    }


    public static void putLongValue(String key, long n) {

        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, n);
        editor.apply();
    }

    public static long getLongValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
        return pref.getLong(key, -1);
    }

    public static long getLongValue(String key, long mDeafult) {
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
        return pref.getLong(key, mDeafult);
    }

    public static void putIntValue(String key, int n) {
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, n);
        editor.apply();
    }

    public static int getIntValue(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE,
                        Context.MODE_PRIVATE);
        return pref.getInt(key, -1);
    }

    public static int getIntValue(String key, int mDeafult) {
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
        return pref.getInt(key, mDeafult);
    }

    public static void putStringValue(String key, String s) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public static String getStringValue(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static String getStringValue(String key, String defaultValue) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public static void putBooleanValue(String key, Boolean b) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static boolean getBooleanValue(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static boolean getBooleanValue(String key, boolean mDefault) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, mDefault);
    }

    public static void putFloatValue(String key, float f) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.apply();
    }

    public static float getFloatValue(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getFloat(key, 0.0f);
    }

    public static float getFloatValue(String key, float mDefault) {
        SharedPreferences pref = context.getSharedPreferences(APP_PREFERENCE,
                Context.MODE_PRIVATE);
        return pref.getFloat(key, mDefault);
    }

    public static void putStringSet(String key, Set<String> s) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, s);
        editor.apply();
    }

    public static Set<String> getStringSet(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.getStringSet(key, null);
    }

    public static void remove(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean isKeyExist(String key) {
        SharedPreferences pref = context.
                getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        return pref.contains(key);
    }




}
