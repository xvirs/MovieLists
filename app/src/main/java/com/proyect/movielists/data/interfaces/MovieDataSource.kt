package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.dto.MovieDetailsDto
import com.proyect.movielists.utils.StatusResult

interface MovieDataSource {
    suspend fun getMovie(movieId: String): StatusResult<MovieDetailsDto>
}