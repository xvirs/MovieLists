package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.LoginRequestDto
import com.proyect.movielists.data.models.LoginResponseDto
import com.proyect.movielists.data.models.SessionTokenRequestDto
import com.proyect.movielists.data.models.SessionTokenResponseDto
import com.proyect.movielists.utils.StatusResult

interface AuthDataSource {
    suspend fun requestToken(): StatusResult<LoginResponseDto>
    suspend fun validateLogin(loginRequestDto : LoginRequestDto): StatusResult<LoginResponseDto>
    suspend fun createSession(sessionTokenRequestDto: SessionTokenRequestDto): StatusResult<SessionTokenResponseDto>
}