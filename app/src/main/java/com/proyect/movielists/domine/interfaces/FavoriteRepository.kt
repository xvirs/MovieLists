package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.FavoriteRequest
import com.proyect.movielists.domine.models.FavoriteResponse
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.utils.StatusResult

interface FavoriteRepository {
    suspend fun getFavorites(): StatusResult<MovieFavResponse>
    suspend fun addOrRemoveFavorite(
        favoriteRequest: FavoriteRequest
    ): StatusResult<FavoriteResponse>

}