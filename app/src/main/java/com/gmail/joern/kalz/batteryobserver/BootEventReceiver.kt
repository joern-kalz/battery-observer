package com.gmail.joern.kalz.batteryobserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootEventReceiver : BroadcastReceiver() {

    @Inject lateinit var batteryCheckService: BatteryCheckService

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            batteryCheckService.activate()
        }
    }
}