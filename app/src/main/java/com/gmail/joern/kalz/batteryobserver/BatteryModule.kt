package com.gmail.joern.kalz.batteryobserver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultNotificationChannel

@Module
@InstallIn(ApplicationComponent::class)
object BatteryModule {

    @Provides
    fun workManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    fun batteryChangedIntentFilter(): IntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

    @Provides
    fun notificationManagerCompat(@ApplicationContext context: Context): NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    @Provides @DefaultNotificationChannel
    fun defaultNotificationChannel() = "default"

    @Provides
    fun notificationCompatBuilder(
        @ApplicationContext context: Context,
        @DefaultNotificationChannel channel: String
    ): NotificationCompat.Builder =
        NotificationCompat.Builder(context, channel)
}