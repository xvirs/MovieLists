package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.utils.StatusResult

class RequestTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): StatusResult<LoginResponse> {
        return authRepository.requestToken()
    }
}