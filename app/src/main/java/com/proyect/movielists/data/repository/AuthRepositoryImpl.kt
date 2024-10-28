package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.AuthDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.models.DeleteSessionTokenRequestDto
import com.proyect.movielists.data.models.DeleteSessionTokenResponseDto
import com.proyect.movielists.data.models.mappers.toSessionTokenResponse
import com.proyect.movielists.data.models.LoginRequestDto
import com.proyect.movielists.data.models.SessionTokenRequestDto
import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val sessionDataStore: SessionDataStore
) : AuthRepository {

    override suspend fun login(
        email: String, password: String
    ): StatusResult<SessionTokenResponse> {
        when (val requestTokenImpl = authDataSource.requestToken()) {
            is StatusResult.Success -> {
                when (val validateLoginImpl = authDataSource.validateLogin(
                    LoginRequestDto(
                        email,
                        password,
                        requestTokenImpl.value.requestToken
                    )
                )) {
                    is StatusResult.Success -> {
                        when (val createSessionImpl =
                            authDataSource.createSession(SessionTokenRequestDto(validateLoginImpl.value.requestToken))) {
                            is StatusResult.Success -> {
                                sessionDataStore.saveSessionId(createSessionImpl.value.sessionId!!)
                                return StatusResult.Success(createSessionImpl.value.toSessionTokenResponse())
                            }

                            is StatusResult.Error -> return StatusResult.Error(createSessionImpl.message)
                        }
                    }
                    is StatusResult.Error -> return StatusResult.Error(validateLoginImpl.message)
                }
            }
            is StatusResult.Error -> return StatusResult.Error(requestTokenImpl.message)
        }
    }

    override suspend fun deleteSession(): StatusResult<DeleteSessionTokenResponseDto> {
        when (val deleteSessionImpl =
            authDataSource.deleteSession(DeleteSessionTokenRequestDto(getRequestToken()))) {
            is StatusResult.Success -> {
                return StatusResult.Success(deleteSessionImpl.value)
            }

            is StatusResult.Error -> return StatusResult.Error(deleteSessionImpl.message)
        }
    }

    private suspend fun getRequestToken(): String {
        val requestToken = when (sessionDataStore.sessionIdFlow.first()) {
            is StatusResult.Success -> (sessionDataStore.sessionIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> { "Error al obtener el Token local de sesión" }
        }
        return requestToken!!
    }
}