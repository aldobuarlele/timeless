package com.rivaldo.timeless

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.rivaldo.timeless.data.local.notification.NotificationChannelManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Base Application class for Timeless.
 * Initializes Hilt dependency injection and app-wide configurations.
 */
@HiltAndroidApp
class TimelessApp : Application(), Configuration.Provider {

    @Inject
    lateinit var notificationChannelManager: NotificationChannelManager

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        // Create the notification channel for Android 8+ (required before any notification)
        notificationChannelManager.createNotificationChannel()
    }

    /**
     * Provides the WorkManager configuration using HiltWorkerFactory
     * for dependency injection into Workers.
     */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
