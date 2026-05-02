package com.rivaldo.timeless.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.rivaldo.timeless.data.local.dao.DailyLogDao
import com.rivaldo.timeless.data.local.entity.DailyLogEntity
import com.rivaldo.timeless.data.local.entity.MediaAttachmentEntity
import com.rivaldo.timeless.data.local.media.MediaMetadataExtractor
import com.rivaldo.timeless.data.local.media.MicroThumbnailGenerator
import com.rivaldo.timeless.domain.model.DailyLog
import com.rivaldo.timeless.domain.model.MediaAttachment
import com.rivaldo.timeless.domain.repository.DailyLogRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [DailyLogRepository].
 *
 * Handles:
 * - Taking persistable URI permissions for gallery access after reboot.
 * - Extracting EXIF metadata (Lat/Long, timestamp) via [MediaMetadataExtractor].
 * - Generating a tiny Base64 micro-thumbnail via [MicroThumbnailGenerator].
 * - Mapping between Room entities and domain models.
 *
 * IMPORTANT: Does NOT copy image files into app storage. Only stores URI strings.
 */
@Singleton
class DailyLogRepositoryImpl @Inject constructor(
    private val dao: DailyLogDao,
    private val thumbnailGenerator: MicroThumbnailGenerator,
    private val metadataExtractor: MediaMetadataExtractor,
    @ApplicationContext private val context: Context
) : DailyLogRepository {

    override suspend fun saveDailyLog(log: DailyLog, attachments: List<MediaAttachment>) {
        val entity = log.toEntity()
        val attachmentEntities = attachments.map { it.toEntity() }
        dao.saveLogWithAttachments(entity, attachmentEntities)
    }

    override suspend fun processPhotoUri(uri: String, logDate: String): MediaAttachment? {
        val photoUri = Uri.parse(uri)
        return processSelectedPhoto(photoUri, logDate)
    }

    override fun getLogByDate(date: String): Flow<DailyLog?> {
        return dao.getLogByDate(date).map { entity ->
            entity?.let { logEntity ->
                logEntity.toDomainModel(emptyList())
            }
        }
    }

    override fun getAllLogs(): Flow<List<DailyLog>> {
        return dao.getAllLogs().map { entities ->
            entities.map { entity ->
                entity.toDomainModel(emptyList())
            }
        }
    }

    /**
     * Process a selected photo URI:
     * 1. Take persistable URI permission for long-term access.
     * 2. Extract EXIF metadata (GPS coordinates and capture timestamp).
     * 3. Generate a tiny Base64 micro-thumbnail (<5KB).
     * 4. Resolve the original file name.
     *
     * @param uri The content URI from the photo picker.
     * @param logDate The date of the parent log entry (YYYY-MM-DD).
     * @return A [MediaAttachment] domain object, or null if processing fails.
     */
    suspend fun processSelectedPhoto(uri: Uri, logDate: String): MediaAttachment? {
        return try {
            // Step 1: Take persistable permission for gallery URI access after reboot
            context.contentResolver.takePersistableUriPermission(
                uri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Step 2: Extract EXIF metadata
            val exifData = metadataExtractor.extract(uri)

            // Step 3: Generate micro-thumbnail
            val microThumbnail = thumbnailGenerator.generate(uri)

            // Step 4: Resolve file name
            val fileName = resolveFileName(uri) ?: "photo_${System.currentTimeMillis()}.jpg"

            MediaAttachment(
                id = java.util.UUID.randomUUID().toString(),
                logDate = logDate,
                uri = uri.toString(),
                fileName = fileName,
                latitude = exifData.latitude,
                longitude = exifData.longitude,
                takenAtMillis = exifData.takenAtMillis,
                microThumbnailBase64 = microThumbnail
            )
        } catch (e: SecurityException) {
            // Some providers (e.g. Google Photos) may not support persistable URI permission
            // Return the attachment without persistable permission — will work during session
            resolveFallbackAttachment(uri, logDate)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Fallback processing when persistable URI permission fails.
     * Still attempts to generate thumbnail and extract metadata.
     */
    private suspend fun resolveFallbackAttachment(uri: Uri, logDate: String): MediaAttachment? {
        return try {
            val exifData = metadataExtractor.extract(uri)
            val microThumbnail = thumbnailGenerator.generate(uri)
            val fileName = resolveFileName(uri) ?: "photo_${System.currentTimeMillis()}.jpg"

            MediaAttachment(
                id = java.util.UUID.randomUUID().toString(),
                logDate = logDate,
                uri = uri.toString(),
                fileName = fileName,
                latitude = exifData.latitude,
                longitude = exifData.longitude,
                takenAtMillis = exifData.takenAtMillis,
                microThumbnailBase64 = microThumbnail
            )
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Resolve the display file name from a content URI using [OpenableColumns].
     */
    private fun resolveFileName(uri: Uri): String? {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0 && cursor.moveToFirst()) {
                cursor.getString(nameIndex)
            } else null
        }
    }

    // --- Mapping Extensions ---

    private fun DailyLog.toEntity(): DailyLogEntity {
        return DailyLogEntity(
            date = date,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun MediaAttachment.toEntity(): MediaAttachmentEntity {
        return MediaAttachmentEntity(
            id = id,
            logDate = logDate,
            uri = uri,
            fileName = fileName,
            latitude = latitude,
            longitude = longitude,
            takenAtMillis = takenAtMillis,
            microThumbnailBase64 = microThumbnailBase64
        )
    }

    fun DailyLogEntity.toDomainModel(
        attachments: List<MediaAttachment>
    ): DailyLog {
        return DailyLog(
            date = date,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            mediaAttachments = attachments
        )
    }

    fun MediaAttachmentEntity.toDomainModel(): MediaAttachment {
        return MediaAttachment(
            id = id,
            logDate = logDate,
            uri = uri,
            fileName = fileName,
            latitude = latitude,
            longitude = longitude,
            takenAtMillis = takenAtMillis,
            microThumbnailBase64 = microThumbnailBase64
        )
    }
}
