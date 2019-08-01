package com.alihamuh.ali.tasbeeh.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceDialogFragmentCompat;

import com.alihamuh.ali.tasbeeh.CommonAppClass;

public class DialogPrefFragCompat extends PreferenceDialogFragmentCompat {
    int totalNumberOfZikrs=14;

    public static DialogPrefFragCompat newInstance(String key) {
        final DialogPrefFragCompat fragment = new DialogPrefFragCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            settings = CommonAppClass.getAppContext().getSharedPreferences("MyPrefsFile", 0);
            editor = settings.edit();
            for(int counter =1;counter<=totalNumberOfZikrs;counter++){
                editor.putInt(getStringResourceByName("zikr_counter_key_"+counter), 0);
            }
            editor.apply();

        }
    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", CommonAppClass.getAppContext().getPackageName());
        return getString(resId);
    }
}
