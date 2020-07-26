package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) {
    private val lowerLimitKey = context.getString(R.string.lower_limit)
    private val upperLimitKey = context.getString(R.string.upper_limit)

    var lowerStateOfChargeLimit: Int
        get() = sharedPreferences.getInt(lowerLimitKey, 25)
        set(value) {
            with(sharedPreferences.edit()) {
                putInt(lowerLimitKey, value)
                apply()
            }
        }

    var upperStateOfChargeLimit: Int
        get() = sharedPreferences.getInt(upperLimitKey, 75)
        set(value) {
            with(sharedPreferences.edit()) {
                putInt(upperLimitKey, value)
                apply()
            }
        }
}