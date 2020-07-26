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
    private val batteryNotificationUpdater: BatteryNotificationUpdater
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        batteryNotificationUpdater.update()
        return Result.success()
    }
}