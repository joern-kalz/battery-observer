package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val minimumBatteryLevel = 20

class BatteryCheckWorker @WorkerInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationService: NotificationService,
    private val batteryChangedIntentFilter: IntentFilter
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val batteryLevel = checkBatteryLevel()

        if (batteryLevel != null) {
            val title = context.getString(R.string.low_battery_title)
            val text = context.getString(R.string.low_battery_description, minimumBatteryLevel)

            notificationService.show(title, text)
        }

        return Result.success()
    }

    private fun checkBatteryLevel(): Float? {
        val batteryStatus: Intent? = context.registerReceiver(null, batteryChangedIntentFilter)

        return batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }
    }
}