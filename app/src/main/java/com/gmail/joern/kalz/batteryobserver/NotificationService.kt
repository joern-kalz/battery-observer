package com.gmail.joern.kalz.batteryobserver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val NOTIFICATION_ID = 1

class NotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val buildVersionProvider: BuildVersionProvider,
    private val notificationManagerCompat: NotificationManagerCompat,
    private val notificationCompatBuilder: NotificationCompat.Builder,
    @DefaultNotificationChannel private val channel: String
) {

    private var initialized = false

    fun show(title: String, text: String) {
        initialize()

        val builder = notificationCompatBuilder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    private fun initialize() {
        if (initialized || buildVersionProvider.isBuildVersionBefore26()) {
            return
        }

        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channel, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}