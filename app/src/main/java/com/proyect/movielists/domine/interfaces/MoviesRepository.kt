package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult


interface MoviesRepository {
    suspend fun getMovieList(movieListType : MovieListType) : StatusResult<MoviesResponse>
    suspend fun searchMovieList(query: String): StatusResult<MoviesResponse>
}