package com.gmail.joern.kalz.batteryobserver

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.BDDMockito.verify

class NotificationServiceTest {

    private val context = mock(Context::class.java)
    private val buildVersionProvider = mock(BuildVersionProvider::class.java)
    private val notificationManagerCompat = mock(NotificationManagerCompat::class.java)
    private val notificationCompatBuilder = mock(NotificationCompat.Builder::class.java)
    private val channel = "testChannel"

    private val notificationService = NotificationService(
        context,
        buildVersionProvider,
        notificationManagerCompat,
        notificationCompatBuilder,
        channel
    )

    private val notificationManager = mock(NotificationManager::class.java)
    private val notification = mock(Notification::class.java)

    @Before
    fun setup() {
        given(context.getString(anyInt()))
            .willReturn("")
        given(context.getSystemService(Context.NOTIFICATION_SERVICE))
            .willReturn(notificationManager)
        given(notificationCompatBuilder.setSmallIcon(anyInt()))
            .willReturn(notificationCompatBuilder)
        given(notificationCompatBuilder.setContentTitle(anyString()))
            .willReturn(notificationCompatBuilder)
        given(notificationCompatBuilder.setContentText(anyString()))
            .willReturn(notificationCompatBuilder)
        given(notificationCompatBuilder.setAutoCancel(anyBoolean()))
            .willReturn(notificationCompatBuilder)
        given(notificationCompatBuilder.setPriority(anyInt()))
            .willReturn(notificationCompatBuilder)
    }

    @Test
    fun shouldShowNotification() {
        given(buildVersionProvider.isBuildVersionBefore26()).willReturn(false)
        given(notificationCompatBuilder.build()).willReturn(notification)

        notificationService.show("", "")

        verify(notificationManagerCompat).notify(anyInt(), eq(notification))
    }
}