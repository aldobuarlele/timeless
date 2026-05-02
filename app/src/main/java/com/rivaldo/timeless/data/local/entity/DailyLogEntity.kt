package com.rivaldo.timeless.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a single daily micro-diary log entry.
 * PK is the date string (YYYY-MM-DD) to enforce exactly one log per day.
 */
@Entity(tableName = "daily_log")
data class DailyLogEntity(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)
