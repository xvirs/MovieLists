package com.proyect.movielists.domine.usecase

import com.proyect.movielists.data.models.DeleteSessionTokenResponseDto
import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.utils.StatusResult

class DeleteSessionUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): StatusResult<DeleteSessionTokenResponseDto> {
        return authRepository.deleteSession()
    }
}