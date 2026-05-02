package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.UserProfile
import com.rivaldo.timeless.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving the user's profile.
 * Returns a Flow for reactive observation of profile data.
 */
class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<UserProfile?> {
        return repository.getProfile()
    }
}
