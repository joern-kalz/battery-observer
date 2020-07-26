package com.gmail.joern.kalz.batteryobserver

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BatteryModuleTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldProvideWorkManager() {
        assertNotNull(BatteryModule.workManager(context))
    }

    @Test
    fun shouldProvideBatteryChangedIntentFilter() {
        assertNotNull(BatteryModule.batteryChangedIntentFilter())
    }

    @Test
    fun shouldProvideNotificationManagerCompat() {
        assertNotNull(BatteryModule.notificationManagerCompat(context))
    }

    @Test
    fun shouldDefaultNotificationChannel() {
        assertNotNull(BatteryModule.defaultNotificationChannel())
    }

    @Test
    fun shouldProvideNotificationCompatBuilder() {
        assertNotNull(BatteryModule.notificationCompatBuilder(context, "test"))
    }

    @Test
    fun shouldProvideSharedPreferences() {
        assertNotNull(BatteryModule.sharedPreferences(context))
    }

}