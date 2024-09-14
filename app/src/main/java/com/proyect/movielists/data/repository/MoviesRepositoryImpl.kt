package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.MoviesDataSource
import com.proyect.movielists.data.mappers.toMovieListsResponse
import com.proyect.movielists.domine.interfaces.MoviesRepository
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult

class MoviesRepositoryImpl(private val moviesDataSource: MoviesDataSource): MoviesRepository {
    override suspend fun getMovieList(movieListType: MovieListType): StatusResult<MoviesResponse> {
        return when(val result = moviesDataSource.getPopularList(movieListType)){
            is StatusResult.Success -> StatusResult.Success(result.value.toMovieListsResponse())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

    override suspend fun searchMovieList(query: String): StatusResult<MoviesResponse> {
        return when(val result = moviesDataSource.searchMovieList(query)){
            is StatusResult.Success -> StatusResult.Success(result.value.toMovieListsResponse())
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }

}