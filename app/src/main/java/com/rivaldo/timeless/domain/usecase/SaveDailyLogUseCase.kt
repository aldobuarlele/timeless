package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.model.MediaAttachment
import com.rivaldo.timeless.domain.repository.DailyLogRepository
import javax.inject.Inject

/**
 * Use case for saving a daily micro-diary log entry.
 *
 * Responsibilities:
 * 1. Validate that the content meets the minimum requirement (1 sentence).
 * 2. Process each selected photo URI to extract EXIF data and generate micro-thumbnails.
 * 3. Persist the log and its media attachments via [DailyLogRepository].
 */
class SaveDailyLogUseCase @Inject constructor(
    private val repository: DailyLogRepository
) {
    /**
     * Minimum character count for a valid sentence (at least ~50 chars).
     * This ensures the user writes at least one meaningful sentence.
     */
    companion object {
        const val MIN_CONTENT_LENGTH = 50
    }

    /**
     * Execute the save operation.
     *
     * @param date The date string in YYYY-MM-DD format.
     * @param content The journal text content.
     * @param photoUris List of content URI strings for selected photos.
     * @return [SaveResult] indicating success or specific validation failure.
     */
    suspend operator fun invoke(
        date: String,
        content: String,
        photoUris: List<String>
    ): SaveResult {
        // Validate: at least 1 sentence (50+ characters)
        val trimmedContent = content.trim()
        if (trimmedContent.length < MIN_CONTENT_LENGTH) {
            return SaveResult.ContentTooShort
        }

        // Process photos: extract EXIF + generate micro-thumbnails
        val attachments = mutableListOf<MediaAttachment>()
        for (uri in photoUris) {
            val attachment = repository.processPhotoUri(uri, date)
            if (attachment != null) {
                attachments.add(attachment)
            }
        }

        // Create domain model
        val now = System.currentTimeMillis()
        val log = DailyLog(
            date = date,
            content = trimmedContent,
            createdAt = now,
            updatedAt = now,
            mediaAttachments = attachments
        )

        // Persist
        repository.saveDailyLog(log, attachments)

        return SaveResult.Success(log, attachments)
    }

    /**
     * Sealed class representing the possible save operation outcomes.
     */
    sealed class SaveResult {
        data object ContentTooShort : SaveResult()
        data class Success(
            val log: DailyLog,
            val attachments: List<MediaAttachment>
        ) : SaveResult()
    }
}
