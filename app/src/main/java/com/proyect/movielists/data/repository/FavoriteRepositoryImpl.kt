package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.FavoriteDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.models.mappers.toData
import com.proyect.movielists.data.models.mappers.toDomain
import com.proyect.movielists.domine.interfaces.FavoriteRepository
import com.proyect.movielists.domine.models.FavoriteRequest
import com.proyect.movielists.domine.models.FavoriteResponse
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.first

class FavoriteRepositoryImpl(
    private val favoriteDataSource: FavoriteDataSource,
    private val sessionDataStore : SessionDataStore
) : FavoriteRepository {
    override suspend fun getFavorites(): StatusResult<MovieFavResponse> {
        return when (val result = favoriteDataSource.getFavorites(accountID = getAccountId())) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomain())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun addOrRemoveFavorite(
        favoriteRequest: FavoriteRequest
    ): StatusResult<FavoriteResponse> {
        return when (val result = favoriteDataSource.addOrRemoveFavorite(
            accountID = getAccountId(),
            favoriteRequestDTO = favoriteRequest.toData()
        )) {
            is StatusResult.Success -> StatusResult.Success(result.value.toDomain())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    private suspend fun getAccountId() : Int{
        val accountId = when (sessionDataStore.accountIdFlow.first()){
            is StatusResult.Success -> ( sessionDataStore.accountIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> { "Error al obtener el ID de la cuenta" }
        }
        return accountId!!.toInt()
    }

}