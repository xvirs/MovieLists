package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieRepository

class GetMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: String) = movieRepository.getMovie(movieId)
}