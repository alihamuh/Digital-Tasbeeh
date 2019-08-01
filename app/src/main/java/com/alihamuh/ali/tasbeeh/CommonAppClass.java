package com.alihamuh.ali.tasbeeh;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CommonAppClass extends Application {

    private static Context context;
    private static int mode=0;
    private static Boolean ThemeChanged =false;

    public void onCreate() {
        super.onCreate();
        CommonAppClass.context = getApplicationContext();

        SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(this);

        mode =Integer.parseInt(customeTheme.getString("theme_list","0"));
    }

    public static Context getAppContext() {
        return CommonAppClass.context;
    }

    public static void setMode(int mode) {
        CommonAppClass.mode = mode;
    }

    public static int getMode() {
        return mode;
    }

    public static void setThemeChanged(Boolean themeChanged) {
        ThemeChanged = themeChanged;
    }

    public static Boolean getThemeChanged() {
        return ThemeChanged;
    }
}
