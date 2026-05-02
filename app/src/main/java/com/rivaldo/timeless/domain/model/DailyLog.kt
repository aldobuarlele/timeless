package com.rivaldo.timeless.domain.model

/**
 * Pure domain model representing a single daily micro-diary log entry.
 *
 * @property date The date string in YYYY-MM-DD format (primary key).
 * @property content The journal text content (minimum 1 sentence).
 * @property createdAt Timestamp (epoch millis) when the log was first created.
 * @property updatedAt Timestamp (epoch millis) of the last modification.
 * @property mediaAttachments List of photos/attachments associated with this log.
 */
data class DailyLog(
    val date: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val mediaAttachments: List<MediaAttachment> = emptyList()
)
