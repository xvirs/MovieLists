package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.Flow

class GetMovieListsUseCase(private val repository: MovieListRepository) {

    fun executeFlow(): Flow<StatusResult<GetMovieListsResponse>> {
        return repository.getMovieListsFlow()
    }

    suspend fun execute(): StatusResult<GetMovieListsResponse> {
        return repository.getMovieLists()
    }
}
