package com.rivaldo.timeless.data.local.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Generates a tiny Base64-encoded micro-thumbnail from a given image URI.
 *
 * The thumbnail is designed to be <5KB in Base64 string length for
 * efficient storage in Room, providing a graceful fallback if the
 * original gallery image is deleted (graceful degradation / broken link).
 *
 * Process:
 * 1. Decode the source image at a very low resolution (max 32px).
 * 2. Compress as JPEG at decreasing quality until <5KB.
 * 3. Encode the compressed bytes to Base64 string.
 */
@Singleton
class MicroThumbnailGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val MAX_THUMBNAIL_SIZE_PX = 32
        private const val MAX_BASE64_SIZE_BYTES = 5_000 // ~5KB
        private const val INITIAL_QUALITY = 20
        private const val MIN_QUALITY = 5
    }

    /**
     * Generate a Base64 micro-thumbnail from the given content URI.
     *
     * @param uri The content URI of the original image.
     * @return Base64-encoded string of the micro-thumbnail, or null if decoding fails.
     */
    fun generate(uri: Uri): String? {
        return try {
            // Step 1: Decode at very low resolution
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            } ?: return null

            // Calculate sample size to get close to MAX_THUMBNAIL_SIZE_PX
            options.inSampleSize = calculateInSampleSize(
                options.outWidth, options.outHeight, MAX_THUMBNAIL_SIZE_PX, MAX_THUMBNAIL_SIZE_PX
            )

            // Decode actual bitmap at reduced size
            options.inJustDecodeBounds = false
            val bitmap = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, options)
            } ?: return null

            // Step 2 & 3: Compress and encode
            encodeToBase64WithinLimit(bitmap)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Encode the bitmap to Base64, compressing until under the 5KB limit.
     */
    private fun encodeToBase64WithinLimit(bitmap: Bitmap): String {
        var quality = INITIAL_QUALITY
        var bytes: ByteArray

        do {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            bytes = outputStream.toByteArray()
            quality -= 5
        } while (bytes.size > MAX_BASE64_SIZE_BYTES && quality >= MIN_QUALITY)

        return Base64.getEncoder().encodeToString(bytes)
    }

    /**
     * Calculate the largest inSampleSize such that both dimensions
     * are <= maxWidth and maxHeight.
     */
    private fun calculateInSampleSize(
        rawWidth: Int, rawHeight: Int,
        maxWidth: Int, maxHeight: Int
    ): Int {
        var inSampleSize = 1
        if (rawHeight > maxHeight || rawWidth > maxWidth) {
            val halfHeight = rawHeight / 2
            val halfWidth = rawWidth / 2
            while (halfHeight / inSampleSize >= maxHeight &&
                halfWidth / inSampleSize >= maxWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
