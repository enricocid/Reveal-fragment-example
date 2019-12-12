package com.iven.awesometest.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.iven.awesometest.R
import com.iven.awesometest.awesomeTestPreferences

object ThemeHelper {

    @JvmStatic
    fun getDefaultNightMode(context: Context): Int {
        return when (awesomeTestPreferences.theme) {
            context.getString(R.string.theme_pref_light) -> AppCompatDelegate.MODE_NIGHT_NO
            context.getString(R.string.theme_pref_dark) -> AppCompatDelegate.MODE_NIGHT_YES
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM else AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
    }

    //fixed array of pairs (first: accent, second: theme)
    @JvmStatic
    val accents = arrayOf(
        R.color.red,
        R.color.pink,
        R.color.purple,
        R.color.deep_purple,
        R.color.indigo,
        R.color.blue,
        R.color.light_blue,
        R.color.cyan,
        R.color.teal,
        R.color.green,
        R.color.light_green,
        R.color.lime,
        R.color.yellow,
        R.color.amber,
        R.color.orange,
        R.color.deep_orange,
        R.color.brown,
        R.color.grey,
        R.color.blue_grey
    )

    @JvmStatic
    fun updateIconTint(imageView: ImageView, tint: Int) {
        ImageViewCompat.setImageTintList(
            imageView, ColorStateList.valueOf(tint)
        )
    }

    @ColorInt
    @JvmStatic
    fun resolveColorAttr(context: Context, @AttrRes colorAttr: Int): Int {
        val resolvedAttr: TypedValue = resolveThemeAttr(context, colorAttr)
        // resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
        val colorRes =
            if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
        return ContextCompat.getColor(context, colorRes)
    }

    @JvmStatic
    private fun resolveThemeAttr(context: Context, @AttrRes attrRes: Int): TypedValue {
        return TypedValue().apply { context.theme.resolveAttribute(attrRes, this, true) }
    }


    @JvmStatic
    fun getTabIcon(iconIndex: Int): Int {
        return when (iconIndex) {
            0 -> R.drawable.ic_person
            else -> R.drawable.ic_more_horiz
        }
    }
}
