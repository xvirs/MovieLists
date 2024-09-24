package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.utils.StatusResult

interface MovieRepository {
    suspend fun getMovie(movieId: String): StatusResult<MovieDetails>
}