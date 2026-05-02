package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.repository.DailyLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Use case for retrieving a daily log for a specific date.
 *
 * Combines the log entity with its media attachments reactively,
 * returning a unified [DailyLog] domain model.
 */
class GetLogForDateUseCase @Inject constructor(
    private val repository: DailyLogRepository
) {
    /**
     * Retrieve the [DailyLog] for the given date as a reactive Flow.
     *
     * @param date The date string in YYYY-MM-DD format.
     * @return A Flow emitting the daily log (with attachments) or null if none exists.
     */
    operator fun invoke(date: String): Flow<DailyLog?> {
        return repository.getLogByDate(date)
    }
}
