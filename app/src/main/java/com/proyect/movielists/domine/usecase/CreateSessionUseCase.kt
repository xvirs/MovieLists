package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult

class CreateSessionUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): StatusResult<SessionTokenResponse> {
        return authRepository.createSession()
    }
}