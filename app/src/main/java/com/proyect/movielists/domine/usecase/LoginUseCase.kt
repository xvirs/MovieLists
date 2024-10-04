package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): StatusResult<SessionTokenResponse> {
        return authRepository.login(email, password)
    }
}