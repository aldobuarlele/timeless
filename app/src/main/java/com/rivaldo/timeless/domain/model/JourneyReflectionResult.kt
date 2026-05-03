package com.rivaldo.timeless.domain.model

/**
 * Pure domain model representing the result of a journey reflection query
 * for a specific week on the Zen Canvas.
 *
 * @property weekIndex The 0-based index of the week in the Zen Canvas grid.
 * @property weekStartDate The Monday of the week in YYYY-MM-DD format.
 * @property weekEndDate The Sunday of the week in YYYY-MM-DD format.
 * @property logs The list of daily log entries for this week (empty if no entries).
 */
data class JourneyReflectionResult(
    val weekIndex: Int,
    val weekStartDate: String,
    val weekEndDate: String,
    val logs: List<DailyLog>
)
