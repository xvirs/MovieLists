package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.dto.AddMovieToListRequestDto
import com.proyect.movielists.data.models.dto.AddMovieToListResponseDto
import com.proyect.movielists.data.models.dto.CreateMovieListRequestDto
import com.proyect.movielists.data.models.dto.CreateMovieListResponseDto
import com.proyect.movielists.data.models.dto.GetMovieListResponseDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.dto.RemoveListResponseDto
import com.proyect.movielists.data.models.dto.GetMovieListsResponseDto
import com.proyect.movielists.utils.StatusResult

interface MovieListDataSource {
    suspend fun createMovieList(createMovieListRequestDto : CreateMovieListRequestDto, sessionId : String) : StatusResult<CreateMovieListResponseDto>
    suspend fun getMovieLists(sessionId : String, accountId : String) : StatusResult<GetMovieListsResponseDto>
    suspend fun getMovieList(listId : String) : StatusResult<GetMovieListResponseDto>
    suspend fun addMovieToList(addMovieToListRequestDto : AddMovieToListRequestDto, sessionId : String, listId : String) : StatusResult<AddMovieToListResponseDto>
    suspend fun removeMovieFromList(removeMovieFromListRequestDto : RemoveMovieFromListRequestDto, sessionId : String, listId : String) : StatusResult<RemoveMovieFromListResponseDto>
    suspend fun removeList(listId : String, sessionId : String) : StatusResult<RemoveListResponseDto>
}