package com.rivaldo.timeless.domain.usecase

import com.rivaldo.timeless.domain.model.UserProfile
import com.rivaldo.timeless.domain.repository.UserProfileRepository
import javax.inject.Inject

/**
 * Use case for saving the user's profile during onboarding.
 * Encapsulates the business logic of persisting profile data.
 */
class SaveUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile) {
        repository.saveProfile(profile)
    }
}
