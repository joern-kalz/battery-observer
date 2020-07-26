package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import androidx.work.*
import androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor
import org.junit.Test
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.mock
import java.util.*
import java.util.concurrent.Executor

class BatteryCheckWorkerTest {

    private val context = mock(Context::class.java)
    private val workerParameters = createWorkerParametersMock()
    private val batteryNotificationUpdater = mock(BatteryNotificationUpdater::class.java)

    private val batteryCheckWorker = BatteryCheckWorker(
        context,
        workerParameters,
        batteryNotificationUpdater
    )

    @Test
    fun shouldTriggerUpdate() {
        batteryCheckWorker.doWork()
        verify(batteryNotificationUpdater).update()
    }

    private fun createWorkerParametersMock() = WorkerParameters(
        UUID(0,0),
        Data.EMPTY,
        emptyList(), WorkerParameters.RuntimeExtras(), 0,
        mock(Executor::class.java),
        mock(WorkManagerTaskExecutor::class.java),
        WorkerFactory.getDefaultWorkerFactory(),
        mock(ProgressUpdater::class.java),
        mock(ForegroundUpdater::class.java)
    )
}