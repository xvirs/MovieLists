package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.FavoriteRequest
import com.proyect.movielists.domine.models.FavoriteResponse
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoritesFlow(): Flow<StatusResult<MovieFavResponse>>
    suspend fun getFavorites(): StatusResult<MovieFavResponse>
    suspend fun addOrRemoveFavorite(
        favoriteRequest: FavoriteRequest
    ): StatusResult<FavoriteResponse>
}