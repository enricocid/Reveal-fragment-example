package com.iven.awesometest.fragments

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.afollestad.materialdialogs.list.getRecyclerView
import com.iven.awesometest.R
import com.iven.awesometest.adapters.AccentsAdapter
import com.iven.awesometest.ui.ThemeHelper


class PreferencesFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mAccentsDialog: MaterialDialog

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        if (::mAccentsDialog.isInitialized && mAccentsDialog.isShowing) mAccentsDialog.dismiss()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        activity?.let { fragmentActivity ->

            val accentPreference = findPreference<Preference>(getString(R.string.accent_pref))

            accentPreference?.setOnPreferenceClickListener {
                showAccentDialog(fragmentActivity)
                return@setOnPreferenceClickListener true
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        activity?.let {

            when (key) {
                getString(R.string.theme_pref) -> AppCompatDelegate.setDefaultNightMode(
                    ThemeHelper.getDefaultNightMode(
                        it
                    )
                )
            }
        }
    }

    private fun showAccentDialog(activity: Activity) {

        mAccentsDialog = MaterialDialog(activity).show {

            cornerRadius(res = R.dimen.md_corner_radius)
            title(res = R.string.accent_pref_title)

            customListAdapter(
                AccentsAdapter()
            )

            getRecyclerView().apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    companion object {

        internal const val TAG = "PreferencesFragmentTag"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PreferencesFragment.
         */
        @JvmStatic
        fun newInstance() = PreferencesFragment()
    }
}
