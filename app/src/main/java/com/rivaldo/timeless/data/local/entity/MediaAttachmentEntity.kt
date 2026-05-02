package com.rivaldo.timeless.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Room entity representing a media attachment for a daily log entry.
 *
 * Stores ONLY the original gallery URI (never copies image files).
 * Includes EXIF metadata and a tiny Base64 micro-thumbnail for fallback.
 *
 * Foreign key cascades deletion: if a daily_log is deleted, its attachments are removed.
 */
@Entity(
    tableName = "media_attachment",
    foreignKeys = [
        ForeignKey(
            entity = DailyLogEntity::class,
            parentColumns = ["date"],
            childColumns = ["log_date"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["log_date"])]
)
data class MediaAttachmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "log_date")
    val logDate: String,

    @ColumnInfo(name = "uri")
    val uri: String,

    @ColumnInfo(name = "file_name")
    val fileName: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Double? = null,

    @ColumnInfo(name = "taken_at_millis")
    val takenAtMillis: Long? = null,

    @ColumnInfo(name = "micro_thumbnail_base64")
    val microThumbnailBase64: String? = null
)
