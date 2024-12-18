package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.data.models.DeleteSessionTokenResponseDto
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult

interface AuthRepository {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun login(email: String, password: String): StatusResult<SessionTokenResponse>
    suspend fun deleteSession(): StatusResult<DeleteSessionTokenResponseDto>
}