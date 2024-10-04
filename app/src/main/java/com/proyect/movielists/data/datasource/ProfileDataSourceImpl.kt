package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.ProfileDataSource
import com.proyect.movielists.data.models.UserProfileDto
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body

class ProfileDataSourceImpl(private val baseClient: BaseClient) : ProfileDataSource {
    override suspend fun getProfile(sessionId: String): StatusResult<UserProfileDto> {
        val endpoint = "/account"
        val errorMessage = "Error al validar perfil"
        val response = baseClient.get(
            url = endpoint,
            errorMessage = errorMessage,
            valueParams = mapOf("session_id" to sessionId)
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<UserProfileDto>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }
}