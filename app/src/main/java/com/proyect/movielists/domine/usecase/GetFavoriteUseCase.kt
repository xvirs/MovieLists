package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.FavoriteRepository
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.Flow

class GetFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {

    suspend operator fun invoke(): StatusResult<MovieFavResponse> {
        return favoriteRepository.getFavorites()
    }

    suspend fun invokeFlow(): Flow<StatusResult<MovieFavResponse>> {
        return favoriteRepository.getFavoritesFlow()
    }
}