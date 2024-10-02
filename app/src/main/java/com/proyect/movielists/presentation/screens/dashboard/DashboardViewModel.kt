package com.proyect.movielists.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.MoviesUseCase
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val moviesUseCase: MoviesUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
) : ViewModel() {

    private val _moviesState = MutableStateFlow<UIState<List<Movie>>>(UIState.Idle)
    val moviesState: StateFlow<UIState<List<Movie>>> = _moviesState

    private val _movieListsState = MutableStateFlow<UIState<List<ListItem>>>(UIState.Idle)
    val movieListsState: StateFlow<UIState<List<ListItem>>> = _movieListsState

    private val _successMessage = MutableStateFlow("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchMovies(MovieListType.POPULAR)
        fetchMovieLists()
    }

    fun fetchMovies(movieListType: MovieListType) {
        _moviesState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = moviesUseCase.execute(movieListType)) {
                is StatusResult.Success -> {
                    _moviesState.value = UIState.Success(result.value.results)
                }
                is StatusResult.Error -> {
                    _moviesState.value = UIState.Error(result.message)
                }
            }
        }
    }

    private fun fetchMovieLists() {
        _movieListsState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    _movieListsState.value = UIState.Success(result.value.results)
                }
                is StatusResult.Error -> {
                    _movieListsState.value = UIState.Error(result.message)
                }
            }
        }
    }

    fun addMovieToList(listId: String, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addMovieToListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Pelicula agregada exitosamente"
                }
                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }
}
