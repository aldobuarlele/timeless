package com.rivaldo.timeless.domain.model

/**
 * Pure domain model representing a media attachment for a daily log.
 *
 * This model stores ONLY the original gallery URI (no copied files),
 * EXIF metadata for fallback display, and a tiny Base64 micro-thumbnail.
 *
 * @property id Unique identifier for this attachment.
 * @property logDate The date of the parent log entry (YYYY-MM-DD).
 * @property uri The original gallery content URI (never a copied file path).
 * @property fileName The original file name for display purposes.
 * @property latitude GPS latitude extracted from EXIF (nullable).
 * @property longitude GPS longitude extracted from EXIF (nullable).
 * @property takenAtMillis Timestamp extracted from EXIF (nullable).
 * @property microThumbnailBase64 Tiny Base64-encoded thumbnail (<5KB) for graceful fallback.
 */
data class MediaAttachment(
    val id: String,
    val logDate: String,
    val uri: String,
    val fileName: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val takenAtMillis: Long? = null,
    val microThumbnailBase64: String? = null
)
