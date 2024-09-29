package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.utils.StatusResult

class AddMovieToListUseCase(private val repository: MovieListRepository) {
    suspend fun execute(mediaId: Int, listId: String): StatusResult<AddMovieToListResponse> {
        return repository.addMovieToList(AddMovieToListRequest(mediaId), listId)
    }
}
