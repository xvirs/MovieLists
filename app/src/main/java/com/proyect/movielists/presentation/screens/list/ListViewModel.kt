package com.proyect.movielists.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.domine.usecase.GetMovieListUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.RemoveMovieFromListUseCase
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.MovieListUI
import com.proyect.movielists.presentation.models.MovieUI
import com.proyect.movielists.presentation.models.mappers.toMovieListUI
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val removeListUseCase: RemoveListUseCase,
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    private val _listDetailsState = MutableStateFlow<UIState<MovieListUI>>(UIState.Loading)
    val listDetailsState = _listDetailsState.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _listMovies = MutableStateFlow<List<ListItemUI>>(emptyList())
    val listMovies = _listMovies.asStateFlow()

    private val _watchedMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val watchedMovies = _watchedMovies.asStateFlow()

    private val _unwatchedMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val unwatchedMovies = _unwatchedMovies.asStateFlow()


    fun getListDetails(listId: String) {
        _listDetailsState.value = UIState.Loading
        viewModelScope.launch (Dispatchers.IO) {
            when (val result = getMovieListUseCase.execute(listId)) {
                is StatusResult.Success -> {
                    val currentWatchedMovies = _watchedMovies.value.toMutableList()
                    val currentUnwatchedMovies = _unwatchedMovies.value.toMutableList()
                    result.value.toMovieListUI().movies.forEach { movie ->
                            if (isWatched(movie.id)) {
                                if (currentWatchedMovies.none { it.id == movie.id }) {
                                    currentWatchedMovies.add(movie)
                                }
                            } else {
                                if (currentUnwatchedMovies.none { it.id == movie.id }) {
                                    currentUnwatchedMovies.add(movie)
                                }
                            }
                        }
                    _watchedMovies.value = currentWatchedMovies
                    _unwatchedMovies.value = currentUnwatchedMovies
                    _listDetailsState.value = UIState.Success(result.value.toMovieListUI())
                }

                is StatusResult.Error -> {
                    _listDetailsState.value = UIState.Error(result.message)
                }
            }
        }
    }

    fun removeMovieFromList(listId: String, movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeMovieFromListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Película eliminada exitosamente"
                    getListDetails(listId)
                }

                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun removeList(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeListUseCase.execute(listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Lista eliminada exitosamente"
                }

                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }


    private suspend fun isWatched(movieId: Int): Boolean {
        val result = withContext(Dispatchers.IO) {
            getFavoriteUseCase.invoke()
        }
        return when (result) {
            is StatusResult.Success -> {
                result.value.results.any { it.id == movieId }
            }

            is StatusResult.Error -> {
                false
            }
        }
    }
}