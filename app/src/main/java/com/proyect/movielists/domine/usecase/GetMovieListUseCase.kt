package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.utils.StatusResult

class GetMovieListUseCase(private val repository: MovieListRepository) {
    suspend fun execute( listId: String): StatusResult<GetMovieListResponse> {
        return repository.getMovieList(listId)
    }
}