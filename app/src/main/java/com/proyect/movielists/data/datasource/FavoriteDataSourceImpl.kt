package com.proyect.movielists.data.datasource

import com.proyect.movielists.data.interfaces.FavoriteDataSource
import com.proyect.movielists.data.models.FavoriteRequestDTO
import com.proyect.movielists.data.models.FavoriteResponseDTO
import com.proyect.movielists.data.models.MovieFavResponseDTO
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.utils.StatusResult
import io.ktor.client.call.body
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class FavoriteDataSourceImpl(private val baseClient : BaseClient) : FavoriteDataSource {
    override suspend fun getFavorites(accountID: Int): StatusResult<MovieFavResponseDTO> {
        val endpoint = "/account/${accountID}/favorite/movies"
        val errorMessage = "Error al obtener favoritos"
        val response = baseClient.get(
            url = endpoint,
            language = "es-ES",
            errorMessage = errorMessage
        )
        return if (response.httpResponse != null){
            StatusResult.Success( response.httpResponse.body<MovieFavResponseDTO>() )
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }

    override suspend fun addOrRemoveFavorite(
        accountID: Int,
        favoriteRequestDTO: FavoriteRequestDTO
    ): StatusResult<FavoriteResponseDTO> {
        val endpoint = "/account/${accountID}/favorite"
        val errorMessage = "Error al agregar o remover favorito"
        val response = baseClient.post(
            url = endpoint,
            body = Json.encodeToString(favoriteRequestDTO),
            errorMessage = errorMessage
        )
        return if (response.httpResponse != null) {
            StatusResult.Success(response.httpResponse.body<FavoriteResponseDTO>())
        } else {
            StatusResult.Error(response.errorMessage)
        }
    }
}