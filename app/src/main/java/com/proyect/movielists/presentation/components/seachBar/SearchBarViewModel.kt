package com.proyect.movielists.presentation.components.seachBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.usecase.SearchMoviesUseCase
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchBarViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _listMovies = MutableStateFlow<List<Movie>?>(null)
    val listMovies = _listMovies.asStateFlow()

    private val _moviesResponse = MutableStateFlow<MoviesResponse?>(null)
    val moviesResponse = _moviesResponse.asStateFlow()


    fun someAuthenticatedRequest(search : String) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchMoviesUseCase.execute(search)) {
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