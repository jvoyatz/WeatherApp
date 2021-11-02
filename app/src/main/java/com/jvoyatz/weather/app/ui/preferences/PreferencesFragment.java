package com.jvoyatz.weather.app.ui.preferences;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.jvoyatz.weather.app.R;

/**
 * Preferences used by app to enable/disable certain tasks, like foreground service
 */
public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}