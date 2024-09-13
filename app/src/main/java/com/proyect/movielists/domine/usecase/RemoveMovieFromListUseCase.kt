package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.utils.StatusResult

class RemoveMovieFromListUseCase(private val repository: MovieListRepository) {
    suspend fun execute(mediaId: Int, listId: Int): StatusResult<RemoveMovieFromListResponse> {
        return repository.removeMovieFromList(RemoveMovieFromListRequest(mediaId), listId.toString())
    }
}
