package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.utils.StatusResult

class RemoveListUseCase(private val repository: MovieListRepository) {
    suspend fun execute(listId: Int): StatusResult<RemoveListResponse> {
        return repository.removeList(listId.toString())
    }
}
