package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.JourneyReflectionResult
import com.rivaldo.timeless.domain.repository.DailyLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

/**
 * Use case for fetching journal entries from a specific week on the Zen Canvas.
 *
 * Maps a 0-based week index (from Zen Canvas grid) to a calendar week
 * (Monday–Sunday) using the user's birth date as the epoch anchor.
 *
 * Algorithm:
 * ```
 * birthDate (epoch millis) → LocalDate → previousOrSame(MONDAY) = birthWeekStart
 * weekStartDate = birthWeekStart.plusWeeks(weekIndex)
 * weekEndDate   = weekStartDate.plusDays(6)
 * ```
 *
 * The formatted date strings are then used to query [DailyLogRepository.getLogsByWeekRange].
 */
class GetJourneyReflectionUseCase @Inject constructor(
    private val repository: DailyLogRepository
) {

    /**
     * Invoke the use case to fetch reflection data for a given week.
     *
     * @param weekIndex 0-based index of the week in the Zen Canvas grid.
     * @param birthDate Epoch millis of the user's birth date.
     * @return A Flow emitting [JourneyReflectionResult] for the requested week.
     */
    operator fun invoke(weekIndex: Int, birthDate: Long): Flow<JourneyReflectionResult> {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        // Calculate week boundaries
        val birthInstant = Instant.ofEpochMilli(birthDate)
        val birthLocalDate = birthInstant.atZone(ZoneId.systemDefault()).toLocalDate()
        val birthWeekStart = birthLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val weekStartDate = birthWeekStart.plusWeeks(weekIndex.toLong())
        val weekEndDate = weekStartDate.plusDays(6)

        val startStr = weekStartDate.format(formatter)
        val endStr = weekEndDate.format(formatter)

        return repository.getLogsByWeekRange(startStr, endStr).map { logs ->
            JourneyReflectionResult(
                weekIndex = weekIndex,
                weekStartDate = startStr,
                weekEndDate = endStr,
                logs = logs
            )
        }
    }
}
