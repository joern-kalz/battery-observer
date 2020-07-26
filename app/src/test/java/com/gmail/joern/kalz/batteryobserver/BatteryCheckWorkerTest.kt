package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.work.*
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor
import org.junit.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.eq
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.mock
import java.util.*
import java.util.concurrent.Executor

class BatteryCheckWorkerTest {

    private val context = mock(Context::class.java)
    private val notificationService = mock(NotificationService::class.java)
    private val batteryChangedIntentFilter = mock(IntentFilter::class.java)

    private val workerParameters = WorkerParameters(
        UUID(0,0),
        Data.EMPTY,
        emptyList(), WorkerParameters.RuntimeExtras(), 0,
        mock(Executor::class.java),
        mock(WorkManagerTaskExecutor::class.java),
        WorkerFactory.getDefaultWorkerFactory(),
        mock(ProgressUpdater::class.java),
        mock(ForegroundUpdater::class.java)
    )

    private val batteryCheckWorker = BatteryCheckWorker(
        context,
        workerParameters,
        notificationService,
        batteryChangedIntentFilter
    )

    private val batteryChangedIntent = mock(Intent::class.java)

    @Test
    fun shouldShowNotification() {
        given(context.registerReceiver(null, batteryChangedIntentFilter))
            .willReturn(batteryChangedIntent)
        given(batteryChangedIntent.getIntExtra(eq(BatteryManager.EXTRA_LEVEL), anyInt()))
            .willReturn(50)
        given(batteryChangedIntent.getIntExtra(eq(BatteryManager.EXTRA_SCALE), anyInt()))
            .willReturn(100)
        given(context.getString(anyInt())).willReturn("")
        given(context.getString(anyInt(), any())).willReturn("")

        batteryCheckWorker.doWork()

        verify(notificationService).show(anyString(), anyString())
    }
}