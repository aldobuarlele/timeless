package com.rivaldo.timeless.domain.repository

import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.model.MediaAttachment
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for daily micro-diary log operations.
 *
 * Defines the contract for saving and retrieving daily journal entries
 * along with their associated media attachments.
 */
interface DailyLogRepository {

    /**
     * Save a daily log with its optional media attachments.
     * If a log for the same date already exists, it will be replaced.
     */
    suspend fun saveDailyLog(log: DailyLog, attachments: List<MediaAttachment>)

    /**
     * Process a selected photo URI:
     * 1. Take persistable URI permission for long-term access.
     * 2. Extract EXIF metadata (GPS coordinates and capture timestamp).
     * 3. Generate a tiny Base64 micro-thumbnail (<5KB).
     * 4. Resolve the original file name.
     *
     * @param uri The content URI string from the photo picker.
     * @param logDate The date of the parent log entry (YYYY-MM-DD).
     * @return A [MediaAttachment] domain object, or null if processing fails.
     */
    suspend fun processPhotoUri(uri: String, logDate: String): MediaAttachment?

    /**
     * Retrieve the daily log for a specific date as a reactive Flow.
     */
    fun getLogByDate(date: String): Flow<DailyLog?>

    /**
     * Retrieve all daily logs ordered by date descending.
     */
    fun getAllLogs(): Flow<List<DailyLog>>

    /**
     * Retrieve all logs within a date range (inclusive) ordered by date ascending.
     * Each log will include its associated media attachments.
     *
     * @param startDate Range start in YYYY-MM-DD format (inclusive).
     * @param endDate Range end in YYYY-MM-DD format (inclusive).
     */
    fun getLogsByWeekRange(startDate: String, endDate: String): Flow<List<DailyLog>>
}
