package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MovieListRepository {

    val movieListsFlow: StateFlow<StatusResult<GetMovieListsResponse>?>
    fun getMovieListsFlow(): Flow<StatusResult<GetMovieListsResponse>>

    suspend fun createMovieList(createMovieListRequest : CreateMovieListRequest) : StatusResult<CreateMovieListResponse>
    suspend fun getMovieLists() : StatusResult<GetMovieListsResponse>
    suspend fun getMovieList( listId: String) : StatusResult<GetMovieListResponse>
    suspend fun addMovieToList(addMovieToListRequest : AddMovieToListRequest, listId : String) : StatusResult<AddMovieToListResponse>
    suspend fun removeMovieFromList(removeMovieFromListRequest : RemoveMovieFromListRequest, listId : String) : StatusResult<RemoveMovieFromListResponse>
    suspend fun removeList(listId : String) : StatusResult<RemoveListResponse>
}