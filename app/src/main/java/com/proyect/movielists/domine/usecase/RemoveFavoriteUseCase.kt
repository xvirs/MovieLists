package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.FavoriteRepository
import com.proyect.movielists.domine.models.FavoriteRequest
import com.proyect.movielists.domine.models.FavoriteResponse
import com.proyect.movielists.utils.StatusResult

class RemoveFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend fun execute(movieId: Int): StatusResult<FavoriteResponse> {
        return favoriteRepository.addOrRemoveFavorite(FavoriteRequest(
            mediaType = "movie",
            mediaId = movieId,
            isFavorite = false
        )
        )
    }
}