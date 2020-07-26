package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito.mock

class BatteryNotificationUpdaterTest {

    private val context = mock(Context::class.java)
    private val notificationService = mock(NotificationService::class.java)
    private val batteryChangedIntentFilter = mock(IntentFilter::class.java)
    private val preferencesService = mock(PreferencesService::class.java)

    private val batteryNotificationUpdater = BatteryNotificationUpdater(
        context,
        notificationService,
        batteryChangedIntentFilter,
        preferencesService
    )

    private val batteryChangedIntent = mock(Intent::class.java)

    @Before
    fun setup() {
        given(context.getString(anyInt())).willReturn("")
        given(context.getString(anyInt(), any())).willReturn("")
        given(preferencesService.lowerStateOfChargeLimit).willReturn(25)
        given(preferencesService.upperStateOfChargeLimit).willReturn(75)
    }

    @Test
    fun shouldShowNotificationWhenBelowLowerLimit() {
        setBatteryStateOfCharge(15)
        batteryNotificationUpdater.update()
        verify(notificationService).show(anyString(), anyString())
    }

    @Test
    fun shouldNotShowNotificationWhenWithinLimits() {
        setBatteryStateOfCharge(50)
        batteryNotificationUpdater.update()
        verify(notificationService, never()).show(anyString(), anyString())
    }

    @Test
    fun shouldShowNotificationWhenAboveUpperLimit() {
        setBatteryStateOfCharge(85)
        batteryNotificationUpdater.update()
        verify(notificationService).show(anyString(), anyString())
    }

    private fun setBatteryStateOfCharge(stateOfCharge: Int) {
        given(context.registerReceiver(null, batteryChangedIntentFilter))
            .willReturn(batteryChangedIntent)
        given(batteryChangedIntent.getIntExtra(eq(BatteryManager.EXTRA_LEVEL), anyInt()))
            .willReturn(stateOfCharge)
        given(batteryChangedIntent.getIntExtra(eq(BatteryManager.EXTRA_SCALE), anyInt()))
            .willReturn(100)
    }

}