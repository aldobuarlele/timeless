package com.rivaldo.timeless.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivaldo.timeless.data.local.dao.DailyLogDao
import com.rivaldo.timeless.data.local.dao.UserProfileDao
import com.rivaldo.timeless.data.local.entity.DailyLogEntity
import com.rivaldo.timeless.data.local.entity.MediaAttachmentEntity
import com.rivaldo.timeless.data.local.entity.UserProfileEntity

/**
 * Room database for Timeless app.
 * Manages local persistence for all app entities including user profile,
 * daily micro-diary logs, and media attachments.
 */
@Database(
    entities = [
        UserProfileEntity::class,
        DailyLogEntity::class,
        MediaAttachmentEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TimelessDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao

    abstract fun dailyLogDao(): DailyLogDao
}
