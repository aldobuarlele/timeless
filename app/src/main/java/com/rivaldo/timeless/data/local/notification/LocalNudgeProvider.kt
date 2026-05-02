package com.rivaldo.timeless.data.local.notification

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Provides a static list of warm, reflective motivational nudges in Bahasa Indonesia.
 * Each nudge is designed to encourage the user to write a micro-diary entry
 * without inducing guilt or anxiety (aligned with the anti-anxiety philosophy).
 *
 * Usage: Call [getRandomNudge] to retrieve a random nudge for a notification.
 */
@Singleton
class LocalNudgeProvider @Inject constructor() {

    private val nudges = listOf(
        "Ada momen indah apa hari ini? Yuk, catat sebentar.",
        "Satu kalimat cukup untuk mengabadikan hari ini.",
        "Apa hal kecil yang membuatmu tersenyum hari ini?",
        "Hari ini berlalu, tapi kenangan bisa tetap hidup. Tuliskan.",
        "Luangkan satu menit untuk dirimu. Ceritakan harimu.",
        "Setiap hari menyimpan cerita. Apa ceritamu hari ini?",
        "Refleksi sejenak: apa yang berkesan dari hari ini?",
        "Kata-kata sederhana bisa menyimpan memori indah. Ayo tulis."
    )

    /**
     * Returns a random nudge string from the list.
     */
    fun getRandomNudge(): String {
        return nudges[Random.nextInt(nudges.size)]
    }
}
