package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BatteryNotificationUpdater @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationService: NotificationService,
    private val batteryChangedIntentFilter: IntentFilter,
    private val preferencesService: PreferencesService
) {
    fun update() {
        val batteryLevel = checkBatteryLevel()
        val lowerLimit = preferencesService.lowerStateOfChargeLimit
        val upperLimit = preferencesService.upperStateOfChargeLimit

        when {
            batteryLevel == null -> hideNotification()

            batteryLevel < lowerLimit -> {
                val text = context.getString(R.string.battery_below_limit_text, lowerLimit)
                showNotification(text)
            }

            batteryLevel > upperLimit -> {
                val text = context.getString(R.string.battery_above_limit_text, upperLimit)
                showNotification(text)
            }

            else -> hideNotification()
        }
    }

    private fun showNotification(text: String) {
        notificationService.show(context.getString(R.string.app_name), text)
    }

    private fun hideNotification() {
        notificationService.hide()
    }

    private fun checkBatteryLevel(): Int? {
        val batteryStatus: Intent? = context.registerReceiver(null, batteryChangedIntentFilter)

        return batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            (level * 100 / scale.toFloat()).toInt()
        }
    }

}