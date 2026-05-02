package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.UserProfile
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

/**
 * Pure domain data representing the calculated weeks for the Zen Canvas.
 *
 * @property totalWeeks Total number of weeks in the user's estimated lifespan.
 * @property spentWeeks Number of weeks lived from birth to today.
 * @property remainingWeeks Number of weeks remaining.
 * @property currentWeekIndex The index of the current week (0-based) within the total timeline.
 */
data class TimeCanvasData(
    val totalWeeks: Int,
    val spentWeeks: Int,
    val remainingWeeks: Int,
    val currentWeekIndex: Int
)

/**
 * Use case for calculating the time canvas data.
 *
 * This use case takes a [UserProfile] and computes:
 * - The total lifespan in weeks (based on birthDate + lifeExpectancy).
 * - The weeks already spent (from birthDate to today).
 * - The weeks remaining.
 *
 * Algorithm uses [java.time] APIs (available from minSdk 26 natively)
 * for accurate week calculations across leap years and time zones.
 */
class CalculateTimeCanvasUseCase @Inject constructor() {

    /**
     * Calculate the time canvas data for the given user profile.
     *
     * @param profile The user's profile containing birthDate and lifeExpectancy.
     * @return [TimeCanvasData] with computed week values.
     */
    operator fun invoke(profile: UserProfile): TimeCanvasData {
        val birthInstant = Instant.ofEpochMilli(profile.birthDate)
        val deathInstant = birthInstant.plus(
            profile.lifeExpectancy.toLong(),
            ChronoUnit.YEARS
        )
        val todayInstant = Instant.now()

        // Align all instants to the start of the week (Monday) for cleaner visual grid alignment
        val birthDate = birthInstant.atZone(ZoneId.systemDefault()).toLocalDate()
        val deathDate = deathInstant.atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDate = todayInstant.atZone(ZoneId.systemDefault()).toLocalDate()

        // Adjust to start-of-week (Monday) to avoid partial week edge cases
        val birthWeekStart = birthDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val deathWeekStart = deathDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val todayWeekStart = todayDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

        val totalWeeks = ChronoUnit.WEEKS.between(birthWeekStart, deathWeekStart).toInt()
        val spentWeeks = ChronoUnit.WEEKS.between(birthWeekStart, todayWeekStart).toInt()
            .coerceIn(0, totalWeeks)

        // If death date has passed, remaining is 0
        val remainingWeeks = (totalWeeks - spentWeeks).coerceAtLeast(0)

        // currentWeekIndex is the index of the current week in the full timeline
        val currentWeekIndex = spentWeeks.coerceAtMost(totalWeeks - 1)

        return TimeCanvasData(
            totalWeeks = if (totalWeeks > 0) totalWeeks else 1, // safety: at least 1 week
            spentWeeks = spentWeeks,
            remainingWeeks = remainingWeeks,
            currentWeekIndex = currentWeekIndex
        )
    }
}
