package com.alihamuh.ali.tasbeeh.Settings;

import android.content.Context;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import com.alihamuh.ali.tasbeeh.R;

public class ResetTasbeehDialogPreference extends DialogPreference {
    public ResetTasbeehDialogPreference(Context context) {
        this(context, null);
    }

    public ResetTasbeehDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public ResetTasbeehDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public ResetTasbeehDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
