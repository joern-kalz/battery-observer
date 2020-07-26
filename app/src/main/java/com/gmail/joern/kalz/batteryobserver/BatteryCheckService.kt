package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val WORKER_TAG = "batteryChecker"
private const val WORKER_NAME = "batteryChecker"

class BatteryCheckService @Inject constructor(
    private val workManager: WorkManager
) {

    fun activate() {
        val workRequest = PeriodicWorkRequest
            .Builder(BatteryCheckWorker::class.java,15, TimeUnit.SECONDS)
            .addTag(WORKER_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

}