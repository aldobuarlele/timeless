package com.rivaldo.timeless.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivaldo.timeless.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for accessing and modifying the user profile.
 * Uses Flow for reactive observation of profile data.
 */
@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getProfile(): Flow<UserProfileEntity?>
}
