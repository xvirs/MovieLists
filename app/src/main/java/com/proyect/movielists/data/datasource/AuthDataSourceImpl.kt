package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.AuthDataSource
import com.proyect.movielists.data.models.dto.LoginRequestDto
import com.proyect.movielists.data.models.dto.LoginResponseDto
import com.proyect.movielists.data.models.dto.SessionTokenRequestDto
import com.proyect.movielists.data.models.dto.SessionTokenResponseDto
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataSourceImpl(private val baseClient: BaseClient) : AuthDataSource {
    override suspend fun requestToken(): StatusResult<LoginResponseDto> {
        val endpoint = "/authentication/token/new"
        val errorMessage = "Error al obtener el token temporal"
        val response = baseClient.get(
            url = endpoint,
            body = "",
            errorMessage = errorMessage
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<LoginResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun validateLogin(loginRequestDto : LoginRequestDto): StatusResult<LoginResponseDto> {
        val endpoint = "/authentication/token/validate_with_login"
        val errorMessage = "Error al validar el login"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(loginRequestDto),
            errorMessage = errorMessage
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<LoginResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun createSession(sessionTokenRequestDto: SessionTokenRequestDto): StatusResult<SessionTokenResponseDto> {
        val endpoint = "/authentication/session/new"
        val errorMessage = "Error al crear la sesión"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(sessionTokenRequestDto),
            errorMessage = errorMessage
        )

        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<SessionTokenResponseDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }
}
