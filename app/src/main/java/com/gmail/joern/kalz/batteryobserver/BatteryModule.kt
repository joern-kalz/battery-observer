package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object BatteryModule {

    @Provides
    fun workManager(@ApplicationContext context: Context) =
        WorkManager.getInstance(context)

    @Provides
    fun batteryChangedIntentFilter(@ApplicationContext context: Context) =
        IntentFilter(Intent.ACTION_BATTERY_CHANGED)
}