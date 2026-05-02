package com.rivaldo.timeless.data.local.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the creation and configuration of the notification channel
 * required for Android 8+ (API 26+) to display notifications.
 *
 * Channel: [CHANNEL_ID]
 * Importance: IMPORTANCE_DEFAULT (plays sound, vibrates, but does not
 * show heads-up intrusively — aligns with the anti-anxiety philosophy).
 */
@Singleton
class NotificationChannelManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "timeless_daily_reminder"
        private const val CHANNEL_NAME = "Pengingat Harian"
        private const val CHANNEL_DESC = "Notifikasi untuk mengingatkan Anda menulis jurnal harian"
    }

    /**
     * Creates the notification channel on Android 8+.
     * Safe to call multiple times; Android ignores duplicate creation.
     */
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESC
                // No vibration pattern — gentle nudge, not an alarm
                enableVibration(false)
                setShowBadge(false)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
