package com.proyect.movielists.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.domine.usecase.GetMovieUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movieState = MutableStateFlow<UiState<MovieDetails>>(UiState.Loading)
    val movieState = _movieState.asStateFlow()

    private val _backgroundImage = MutableStateFlow<String?>("")
    val backgroundImage = _backgroundImage.asStateFlow()

    fun getMovie(movieID: String) {
        _movieState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getMovieUseCase.invoke(movieID)) {
                is StatusResult.Error -> {
                    _movieState.value = UiState.Error(response.message)
                }
                is StatusResult.Success -> {
                    _backgroundImage.value = response.value.backdropPath
                    _movieState.value = UiState.Success(response.value)
                }
            }
        }
    }
}
