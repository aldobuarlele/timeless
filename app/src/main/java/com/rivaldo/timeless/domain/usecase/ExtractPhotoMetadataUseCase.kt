package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.MediaAttachment
import com.rivaldo.timeless.domain.repository.DailyLogRepository
import javax.inject.Inject

/**
 * Use case for extracting photo metadata and generating a micro-thumbnail.
 *
 * Validates the URI and delegates to [DailyLogRepository.processPhotoUri]
 * for EXIF extraction, micro-thumbnail generation, and persistable permission handling.
 */
class ExtractPhotoMetadataUseCase @Inject constructor(
    private val repository: DailyLogRepository
) {
    /**
     * Process a selected photo URI and return its metadata + thumbnail.
     *
     * @param uri The content URI string from the photo picker.
     * @param logDate The date of the parent log entry (YYYY-MM-DD).
     * @return A [MediaAttachment] with extracted data, or null if processing fails.
     */
    suspend operator fun invoke(uri: String, logDate: String): MediaAttachment? {
        return repository.processPhotoUri(uri, logDate)
    }
}
