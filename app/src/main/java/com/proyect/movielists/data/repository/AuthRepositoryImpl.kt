package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.AuthDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.models.mappers.toSessionTokenResponse
import com.proyect.movielists.data.models.LoginRequestDto
import com.proyect.movielists.data.models.SessionTokenRequestDto
import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.utils.StatusResult

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
}