package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.ProfileRepository
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.utils.StatusResult

class ProfileUseCase( private val profileRepository: ProfileRepository) {
    suspend fun executeAuthenticatedRequest(): StatusResult<UserProfile> = profileRepository.getProfile()
}