package com.rivaldo.timeless.domain.model

/**
 * Pure domain model representing the user's profile.
 * Free from any framework annotations (Room, etc.).
 * Core business concept for time-awareness calculation.
 */
data class UserProfile(
    val id: String,
    val name: String,
    val birthDate: Long,
    val country: String,
    val lifeExpectancy: Float,
    val homeLatitude: Double,
    val homeLongitude: Double,
    val reminderTime: String
)
