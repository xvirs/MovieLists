package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.MovieDataSource
import com.proyect.movielists.data.models.mappers.toMovieDetails
import com.proyect.movielists.domine.interfaces.MovieRepository
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.utils.StatusResult

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun getMovie(movieId: String): StatusResult<MovieDetails> {
        return when(val result = movieDataSource.getMovie(movieId)){
            is StatusResult.Success -> StatusResult.Success(result.value.toMovieDetails())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }
}