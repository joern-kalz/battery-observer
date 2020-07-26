package com.gmail.joern.kalz.batteryobserver

import androidx.hilt.work.HiltWorkerFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BatteryApplicationTest {

    private val batteryApplication = BatteryApplication()

    @Test
    fun shouldConfigureHiltWorkerFactory() {
        val hiltWorkerFactory = HiltWorkerFactory(emptyMap())
        batteryApplication.workerFactory = hiltWorkerFactory

        val result = batteryApplication.workManagerConfiguration

        assertEquals(hiltWorkerFactory, result.workerFactory)
    }
}