package com.example.weather.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.weather.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}