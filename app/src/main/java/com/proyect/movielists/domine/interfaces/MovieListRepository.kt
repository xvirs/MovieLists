package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.utils.StatusResult

interface MovieListRepository {
    suspend fun createMovieList(createMovieListRequest : CreateMovieListRequest) : StatusResult<CreateMovieListResponse>
    suspend fun getMovieLists() : StatusResult<GetMovieListsResponse>
    suspend fun addMovieToList(addMovieToListRequest : AddMovieToListRequest, listId : String) : StatusResult<AddMovieToListResponse>
    suspend fun removeMovieFromList(removeMovieFromListRequest : RemoveMovieFromListRequest, listId : String) : StatusResult<RemoveMovieFromListResponse>
    suspend fun removeList(listId : String) : StatusResult<RemoveListResponse>
}