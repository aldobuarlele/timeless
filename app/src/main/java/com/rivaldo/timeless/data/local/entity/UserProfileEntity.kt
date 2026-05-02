package com.rivaldo.timeless.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Room entity representing the user's profile and time-awareness configuration.
 * Stores biodata, location context, and reminder preferences.
 */
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "birth_date")
    val birthDate: Long,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "life_expectancy")
    val lifeExpectancy: Float = 70.0f,

    @ColumnInfo(name = "home_latitude")
    val homeLatitude: Double,

    @ColumnInfo(name = "home_longitude")
    val homeLongitude: Double,

    @ColumnInfo(name = "reminder_time")
    val reminderTime: String
)
