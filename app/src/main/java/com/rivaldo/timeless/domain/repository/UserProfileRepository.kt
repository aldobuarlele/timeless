package com.rivaldo.timeless.domain.repository

import com.rivaldo.timeless.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user profile data operations.
 * Defines the contract for saving and retrieving profile data.
 */
interface UserProfileRepository {

    suspend fun saveProfile(profile: UserProfile)

    fun getProfile(): Flow<UserProfile?>
}
