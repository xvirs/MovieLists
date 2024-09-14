package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MoviesRepository

class SearchMoviesUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(query: String) = moviesRepository.searchMovieList(query)
}