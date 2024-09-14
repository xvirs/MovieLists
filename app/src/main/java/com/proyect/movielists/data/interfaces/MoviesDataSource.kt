package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.dto.MoviesResponseDto
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult

interface MoviesDataSource {
    suspend fun getPopularList(movieListType : MovieListType) : StatusResult<MoviesResponseDto>
    suspend fun searchMovieList(query: String): StatusResult<MoviesResponseDto>
}