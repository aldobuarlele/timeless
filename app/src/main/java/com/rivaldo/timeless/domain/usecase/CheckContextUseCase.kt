package com.rivaldo.timeless.domain.usecase

import android.content.Context
import android.os.PowerManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case that checks the current device and time context to determine
 * whether a reminder notification should be shown (US 4.2).
 *
 * Logic:
 * 1. **Time window check**: The current time must be within 1 hour of
 *    the user's configured [reminderTime]. If outside this window,
 *    the notification is suppressed.
 * 2. **Screen state check** (optional for now): If the screen is off/locked
 *    for an extended period, the notification is deferred to avoid
 *    disturbing rest. Currently uses [PowerManager.isInteractive] as a
 *    lightweight hint — full implementation with timing is deferred.
 */
@Singleton
class CheckContextUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * Tolerance window in minutes. Notifications are only delivered
     * if the current time is within this many minutes of [reminderTime].
     */
    companion object {
        private const val TOLERANCE_WINDOW_MINUTES = 60L
    }

    /**
     * Evaluates whether the current context is appropriate for showing
     * a reminder notification.
     *
     * @param reminderTime The user's configured reminder time in "HH:mm" format.
     * @return `true` if it is an appropriate time to notify, `false` otherwise.
     */
    fun invoke(reminderTime: String): Boolean {
        // 1. Time window check
        if (!isWithinTimeWindow(reminderTime)) return false

        // 2. Screen state check (basic — not deferring yet, just a filter)
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isInteractive) return false

        return true
    }

    /**
     * Checks if the current time is within [TOLERANCE_WINDOW_MINUTES] of
     * the configured reminder time.
     */
    private fun isWithinTimeWindow(reminderTime: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val targetTime = LocalTime.parse(reminderTime, formatter)
            val now = LocalTime.now()

            val diff = Duration.between(now, targetTime).abs()
            diff.toMinutes() <= TOLERANCE_WINDOW_MINUTES
        } catch (e: Exception) {
            // If parsing fails, default to allowing the notification
            true
        }
    }
}
