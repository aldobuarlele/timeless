package com.rivaldo.timeless.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rivaldo.timeless.data.local.entity.DailyLogEntity
import com.rivaldo.timeless.data.local.entity.MediaAttachmentEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for daily micro-diary log entries.
 *
 * Provides transactional methods for saving a log with its media attachments,
 * and reactive queries (Flow) for observing log data.
 */
@Dao
interface DailyLogDao {

    /**
     * Insert a single daily log. REPLACE strategy allows updating log for the same date.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: DailyLogEntity)

    /**
     * Update an existing daily log's content and updated timestamp.
     */
    @Query("UPDATE daily_log SET content = :content, updated_at = :updatedAt WHERE date = :date")
    suspend fun updateLog(date: String, content: String, updatedAt: Long)

    /**
     * Retrieve a single log for a given date as a reactive Flow.
     */
    @Query("SELECT * FROM daily_log WHERE date = :date LIMIT 1")
    fun getLogByDate(date: String): Flow<DailyLogEntity?>

    /**
     * Retrieve all logs ordered by date descending (most recent first) as a reactive Flow.
     */
    @Query("SELECT * FROM daily_log ORDER BY date DESC")
    fun getAllLogs(): Flow<List<DailyLogEntity>>

    // --- Media Attachment Queries ---

    /**
     * Insert a list of media attachments for a log entry.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaAttachments(attachments: List<MediaAttachmentEntity>)

    /**
     * Retrieve all media attachments for a given log date.
     */
    @Query("SELECT * FROM media_attachment WHERE log_date = :logDate")
    fun getMediaAttachmentsForDate(logDate: String): Flow<List<MediaAttachmentEntity>>

    /**
     * Delete all media attachments for a given log date.
     * Useful when re-saving a log with updated photos.
     */
    @Query("DELETE FROM media_attachment WHERE log_date = :logDate")
    suspend fun deleteMediaAttachmentsForDate(logDate: String)

    /**
     * Convenience method: saves a log and its media attachments in a single transaction.
     */
    @Transaction
    suspend fun saveLogWithAttachments(
        log: DailyLogEntity,
        attachments: List<MediaAttachmentEntity>
    ) {
        insertLog(log)
        deleteMediaAttachmentsForDate(log.date)
        if (attachments.isNotEmpty()) {
            insertMediaAttachments(attachments)
        }
    }
}
