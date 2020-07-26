package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*

class BootEventReceiverTest {

    val bootEventReceiver = BootEventReceiver()
    val batteryCheckService = mock(BatteryCheckService::class.java)
    val context = mock(Context::class.java)
    val intent = mock(Intent::class.java)

    @Before
    fun setup() {
        bootEventReceiver.batteryCheckService = batteryCheckService
    }

    @Test
    fun shouldActivateBatteryCheck() {
        given(intent.action).willReturn(Intent.ACTION_BOOT_COMPLETED)
        bootEventReceiver.onReceive(context, intent)
        verify(batteryCheckService).activate()
    }
}