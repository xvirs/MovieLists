package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MoviesRepository
import com.proyect.movielists.utils.MovieListType

class MoviesUseCase(private val moviesRepository: MoviesRepository) {
    suspend fun execute(movieListType: MovieListType) = moviesRepository.getPopularList(movieListType)
}