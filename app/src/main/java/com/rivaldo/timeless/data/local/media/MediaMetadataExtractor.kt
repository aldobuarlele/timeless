package com.rivaldo.timeless.data.local.media

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Extracts metadata (GPS coordinates, capture timestamp) from image EXIF data.
 *
 * Uses [ExifInterface] to read the EXIF tags from the original image URI.
 * This data is stored alongside the gallery URI to provide context-aware fallback
 * if the original image is deleted from the gallery ("Broken Link Fallback").
 */
@Singleton
class MediaMetadataExtractor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Data class holding the extracted EXIF metadata.
     */
    data class ExifData(
        val latitude: Double?,
        val longitude: Double?,
        val takenAtMillis: Long?
    )

    /**
     * Extract EXIF metadata from the given image content URI.
     *
     * @param uri The content URI of the image.
     * @return [ExifData] containing latitude, longitude, and capture timestamp.
     *         Null values indicate the tag was not present or could not be parsed.
     */
    fun extract(uri: Uri): ExifData {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                return ExifData(null, null, null)
            }

            val exifInterface = inputStream.use { stream ->
                ExifInterface(stream)
            }

            val latLng = extractLatLng(exifInterface)
            val takenAtMillis = extractDateTime(exifInterface)

            ExifData(
                latitude = latLng?.first,
                longitude = latLng?.second,
                takenAtMillis = takenAtMillis
            )
        } catch (e: Exception) {
            ExifData(null, null, null)
        }
    }

    /**
     * Extract GPS latitude and longitude from EXIF tags.
     * Returns null if either is missing.
     */
    private fun extractLatLng(exif: ExifInterface): Pair<Double, Double>? {
        val latArray = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
        val latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
        val lngArray = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
        val lngRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)

        if (latArray == null || lngArray == null) return null

        return try {
            val latitude = convertRationalLatLonToDouble(latArray, latRef ?: "N")
            val longitude = convertRationalLatLonToDouble(lngArray, lngRef ?: "E")
            Pair(latitude, longitude)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Extract capture datetime from EXIF and convert to epoch millis.
     */
    private fun extractDateTime(exif: ExifInterface): Long? {
        val dateTimeString = exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
            ?: exif.getAttribute(ExifInterface.TAG_DATETIME)
            ?: return null

        return try {
            // Expected EXIF format: "YYYY:MM:DD HH:mm:ss"
            val parts = dateTimeString.split(" ")
            val datePart = parts[0].replace(":", "-")
            val cleaned = if (parts.size > 1) "$datePart ${parts[1]}" else datePart
            java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US)
                .parse(cleaned)
                ?.time
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convert EXIF rational GPS coordinates (e.g., "3/1,30/1,45/1") to decimal degrees.
     */
    private fun convertRationalLatLonToDouble(rational: String, ref: String): Double {
        val parts = rational.split(",")
        val degrees = parts[0].trim().split("/").let {
            it[0].toDouble() / (if (it.size > 1) it[1].toDouble() else 1.0)
        }
        val minutes = parts[1].trim().split("/").let {
            it[0].toDouble() / (if (it.size > 1) it[1].toDouble() else 1.0)
        }
        val seconds = parts[2].trim().split("/").let {
            it[0].toDouble() / (if (it.size > 1) it[1].toDouble() else 1.0)
        }

        var result = degrees + minutes / 60.0 + seconds / 3600.0
        if (ref == "S" || ref == "W") {
            result *= -1
        }
        return result
    }
}
