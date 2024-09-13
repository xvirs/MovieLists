package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.utils.StatusResult

class CreateMovieListUseCase(private val repository: MovieListRepository) {
    suspend fun execute(name: String, description: String, language: String): StatusResult<CreateMovieListResponse> {
        return repository.createMovieList(CreateMovieListRequest(name, description, language))
    }
}
