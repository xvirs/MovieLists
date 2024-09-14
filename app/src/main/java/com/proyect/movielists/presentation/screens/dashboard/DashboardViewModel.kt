package com.proyect.movielists.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.usecase.MoviesUseCase
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _listMovies = MutableStateFlow<List<Movie>?>(null)
    val listMovies: Flow<List<Movie>?> = _listMovies


    private val _moviesResponse = MutableStateFlow<MoviesResponse?>(null)
    val moviesResponse: Flow<MoviesResponse?> = _moviesResponse

    init {
        someAuthenticatedRequest(MovieListType.POPULAR)
    }

    fun someAuthenticatedRequest(movieListType: MovieListType) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = moviesUseCase.execute(movieListType)) {
                is StatusResult.Success -> {
                    _moviesResponse.value = result.value
                    _listMovies.value = result.value.results
                    setLoading(false)
                }
                is StatusResult.Error -> {
                    setLoading(false)
                }
            }
        }
    }

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}