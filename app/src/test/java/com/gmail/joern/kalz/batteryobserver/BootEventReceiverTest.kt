package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*

class BootEventReceiverTest {

    private val bootEventReceiver = BootEventReceiver()
    private val batteryCheckService = mock(BatteryCheckService::class.java)
    private val batteryNotificationUpdater = mock(BatteryNotificationUpdater::class.java)
    private val context = mock(Context::class.java)
    private val intent = mock(Intent::class.java)

    @Before
    fun setup() {
        bootEventReceiver.batteryCheckService = batteryCheckService
        bootEventReceiver.batteryNotificationUpdater = batteryNotificationUpdater
    }

    @Test
    fun shouldActivateBatteryCheck() {
        given(intent.action).willReturn(Intent.ACTION_BOOT_COMPLETED)
        bootEventReceiver.onReceive(context, intent)
        verify(batteryCheckService).activate()
    }
}