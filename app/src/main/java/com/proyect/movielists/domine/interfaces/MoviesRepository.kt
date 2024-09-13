package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult


interface MoviesRepository {
    suspend fun getPopularList(movieListType : MovieListType) : StatusResult<MoviesResponse>
}