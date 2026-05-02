package com.rivaldo.timeless.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rivaldo.timeless.MainActivity
import com.rivaldo.timeless.data.local.notification.LocalNudgeProvider
import com.rivaldo.timeless.data.local.notification.NotificationChannelManager
import com.rivaldo.timeless.domain.model.UserProfile
import com.rivaldo.timeless.domain.usecase.CheckContextUseCase
import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

/**
 * A [CoroutineWorker] that displays a local notification with a warm,
 * reflective nudge to encourage the user to write a micro-diary entry.
 *
 * Before showing the notification, it performs context checks via
 * [CheckContextUseCase] to avoid disturbing the user at inappropriate times.
 *
 * Uses Hilt-assisted injection via [HiltWorker] annotation.
 */
@HiltWorker
class TimelessReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkContextUseCase: CheckContextUseCase,
    private val localNudgeProvider: LocalNudgeProvider,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        // 1. Retrieve user profile to get the reminder time
        val profile: UserProfile? = getUserProfileUseCase().first()

        if (profile == null) {
            // No profile configured — cannot determine reminder time
            return Result.success()
        }

        // 2. Check context (time window + screen state)
        val canNotify = checkContextUseCase.invoke(profile.reminderTime)
        if (!canNotify) {
            // Not an appropriate time — skip notification
            return Result.success()
        }

        // 3. Show notification with a random nudge
        val nudge = localNudgeProvider.getRandomNudge()
        showNotification(nudge)

        return Result.success()
    }

    /**
     * Displays a local notification with the given [nudge] text.
     */
    private fun showNotification(nudge: String) {
        // Tap intent to open the main activity
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            applicationContext,
            NotificationChannelManager.CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Timeless")
            .setContentText(nudge)
            .setStyle(NotificationCompat.BigTextStyle().bigText(nudge))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(
                NOTIFICATION_ID,
                notification
            )
        } catch (e: SecurityException) {
            // POST_NOTIFICATIONS permission not granted — silently ignore
            // Permission request is handled in the UI layer
        }
    }
}
