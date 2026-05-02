package com.rivaldo.timeless.domain.usecase

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.rivaldo.timeless.worker.TimelessReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case that schedules the [TimelessReminderWorker] as a daily
 * periodic task using WorkManager.
 *
 * Configuration for battery efficiency:
 * - [Constraints.requiresBatteryNotLow]: Worker will not run if battery is low.
 * - Interval: 24 hours with a flex interval — the system chooses the optimal
 *   time within a window to minimize battery impact.
 * - [ExistingPeriodicWorkPolicy.KEEP]: If a work request with the same
 *   unique name already exists, it is kept (not recreated).
 */
@Singleton
class ScheduleReminderUseCase @Inject constructor(
    @ApplicationContext private val context: android.content.Context
) {
    companion object {
        private const val UNIQUE_WORK_NAME = "timeless_daily_reminder"
        private const val INTERVAL_HOURS = 24L
        private const val FLEX_INTERVAL_HOURS = 1L
    }

    /**
     * Schedules (or keeps) the daily reminder worker.
     * Safe to call multiple times — will not duplicate the schedule.
     */
    operator fun invoke() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<TimelessReminderWorker>(
            INTERVAL_HOURS, TimeUnit.HOURS,
            FLEX_INTERVAL_HOURS, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
