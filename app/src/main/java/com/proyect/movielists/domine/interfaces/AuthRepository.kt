package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult

interface AuthRepository {
    suspend fun requestToken(): StatusResult<LoginResponse>
    suspend fun validateLogin(username: String, password: String): StatusResult<LoginResponse>
    suspend fun createSession(): StatusResult<SessionTokenResponse>

}