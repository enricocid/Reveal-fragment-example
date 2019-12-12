package com.iven.awesometest

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.iven.awesometest.ui.ThemeHelper

val awesomeTestPreferences: AwesomeTestPreferences by lazy {
    AwesomeApp.prefs
}

class AwesomeApp : Application() {

    companion object {
        lateinit var prefs: AwesomeTestPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = AwesomeTestPreferences(applicationContext)
        AppCompatDelegate.setDefaultNightMode(ThemeHelper.getDefaultNightMode(applicationContext))
    }
}
