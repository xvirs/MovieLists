package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.utils.StatusResult

class GetMovieListsUseCase(private val repository: MovieListRepository) {
    suspend fun execute(): StatusResult<GetMovieListsResponse> {
        return repository.getMovieLists()
    }
}
