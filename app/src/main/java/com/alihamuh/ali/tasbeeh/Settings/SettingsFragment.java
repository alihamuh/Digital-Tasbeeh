package com.alihamuh.ali.tasbeeh.Settings;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.alihamuh.ali.tasbeeh.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.settings);

    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        // Try if the preference is one of our custom Preferences
        DialogFragment dialogFragment = null;
        if (preference instanceof TimePreference) {
            // Create a new instance of TimePreferenceDialogFragment with the key of the related
            // Preference
            dialogFragment = TimePreferenceDialogFragmentCompat
                    .newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(),
                    "android.support.v7.preference" +
                            ".PreferenceFragment.DIALOG");


        }else if (preference instanceof ResetTasbeehDialogPreference) {

            DialogFragment dialog2Fragment = DialogPrefFragCompat.newInstance(preference.getKey());
            dialog2Fragment.setTargetFragment(this, 0);
            dialog2Fragment.show(getFragmentManager(), null);

        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

}
