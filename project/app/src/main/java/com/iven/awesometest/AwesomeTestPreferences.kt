package com.iven.awesometest

import android.content.Context
import androidx.preference.PreferenceManager

class AwesomeTestPreferences(context: Context) {

    private val prefsTheme = context.getString(R.string.theme_pref)
    private val prefsThemeDefault = context.getString(R.string.theme_pref_light)

    private val mPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    var theme
        get() = mPrefs.getString(prefsTheme, prefsThemeDefault)
        set(value) = mPrefs.edit().putString(prefsTheme, value).apply()
}

