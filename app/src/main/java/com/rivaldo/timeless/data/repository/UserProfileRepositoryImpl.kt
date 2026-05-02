package com.rivaldo.timeless.data.repository

import com.rivaldo.timeless.data.local.dao.UserProfileDao
import com.rivaldo.timeless.data.local.entity.UserProfileEntity
import com.rivaldo.timeless.domain.model.UserProfile
import com.rivaldo.timeless.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of UserProfileRepository.
 * Maps between Room Entity (data layer) and Domain Model (domain layer).
 */
@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val dao: UserProfileDao
) : UserProfileRepository {

    override suspend fun saveProfile(profile: UserProfile) {
        dao.insertProfile(profile.toEntity())
    }

    override fun getProfile(): Flow<UserProfile?> {
        return dao.getProfile().map { entity ->
            entity?.toDomainModel()
        }
    }

    private fun UserProfile.toEntity(): UserProfileEntity {
        return UserProfileEntity(
            id = id,
            name = name,
            birthDate = birthDate,
            country = country,
            lifeExpectancy = lifeExpectancy,
            homeLatitude = homeLatitude,
            homeLongitude = homeLongitude,
            reminderTime = reminderTime
        )
    }

    private fun UserProfileEntity.toDomainModel(): UserProfile {
        return UserProfile(
            id = id,
            name = name,
            birthDate = birthDate,
            country = country,
            lifeExpectancy = lifeExpectancy,
            homeLatitude = homeLatitude,
            homeLongitude = homeLongitude,
            reminderTime = reminderTime
        )
    }
}
