package com.proyect.movielists.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.MoviesUseCase
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val moviesUseCase: MoviesUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _movies = MutableStateFlow<List<Movie>?>(null)
    val movies: Flow<List<Movie>?> = _movies

    private val _movieLists = MutableStateFlow<List<ListItem>>(emptyList())
    val movieLists = _movieLists.asStateFlow()

    private val _moviesResponse = MutableStateFlow<MoviesResponse?>(null)
    val moviesResponse: Flow<MoviesResponse?> = _moviesResponse

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        someAuthenticatedRequest(MovieListType.POPULAR)
        getMovieLists()
    }

    fun someAuthenticatedRequest(movieListType: MovieListType) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = moviesUseCase.execute(movieListType)) {
                is StatusResult.Success -> {
                    _moviesResponse.value = result.value
                    _movies.value = result.value.results
                    setLoading(false)
                }
                is StatusResult.Error -> {
                    setLoading(false)
                }
            }
        }
    }

    private fun getMovieLists() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    _movieLists.value = result.value.results
                }

                is StatusResult.Error -> {
                    _errorMessage.value = result.message
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

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}