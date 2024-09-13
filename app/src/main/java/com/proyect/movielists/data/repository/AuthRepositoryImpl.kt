package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.AuthDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.mappers.toLoginResponse
import com.proyect.movielists.data.mappers.toSessionTokenResponse
import com.proyect.movielists.data.models.dto.LoginRequestDto
import com.proyect.movielists.data.models.dto.SessionTokenRequestDto
import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(private val authDataSource : AuthDataSource, private val sessionDataStore : SessionDataStore) : AuthRepository {
    override suspend fun requestToken(): StatusResult<LoginResponse> {
        return when(val result = authDataSource.requestToken()) {
            is StatusResult.Success ->{
                sessionDataStore.saveSessionId(result.value.requestToken!!)
                StatusResult.Success(result.value.toLoginResponse())
            }
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun validateLogin(
        username: String,
        password: String
    ): StatusResult<LoginResponse> {
        val requestToken = when (sessionDataStore.sessionIdFlow.first()){
            is StatusResult.Success -> (sessionDataStore.sessionIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> null
        }
        return when(val result = authDataSource.validateLogin(LoginRequestDto(username, password, requestToken.toString()))) {
            is StatusResult.Success -> StatusResult.Success(result.value.toLoginResponse())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun createSession(): StatusResult<SessionTokenResponse> {
        val requestToken = when (sessionDataStore.sessionIdFlow.first()){
            is StatusResult.Success -> (sessionDataStore.sessionIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> null
        }
        return when(val result = authDataSource.createSession(SessionTokenRequestDto(requestToken.toString()))) {
            is StatusResult.Success -> {
                sessionDataStore.saveSessionId(result.value.sessionId!!)
                StatusResult.Success(result.value.toSessionTokenResponse())
            }
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

}