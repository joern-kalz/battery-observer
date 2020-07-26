package com.gmail.joern.kalz.batteryobserver

import androidx.work.WorkManager
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class BatteryCheckServiceTest {

    @Test
    fun shouldActivate() {
        val workManager = mock(WorkManager::class.java)
        val batteryChecker = BatteryCheckService(workManager)
        batteryChecker.activate()
        verify(workManager).enqueueUniquePeriodicWork(any(), any(), any())
    }
}