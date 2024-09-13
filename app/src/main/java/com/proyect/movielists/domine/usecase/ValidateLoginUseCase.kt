package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.utils.StatusResult

class ValidateLoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): StatusResult<LoginResponse> {
        return authRepository.validateLogin(username, password)
    }
}