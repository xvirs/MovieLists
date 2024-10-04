package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.FavoriteRequestDTO
import com.proyect.movielists.data.models.FavoriteResponseDTO
import com.proyect.movielists.data.models.MovieFavResponseDTO
import com.proyect.movielists.utils.StatusResult

interface FavoriteDataSource {
    suspend fun getFavorites(accountID: Int): StatusResult<MovieFavResponseDTO>
    suspend fun addOrRemoveFavorite(accountID: Int, favoriteRequestDTO : FavoriteRequestDTO): StatusResult<FavoriteResponseDTO>
}