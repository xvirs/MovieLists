package com.proyect.movielists.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.domine.usecase.AddFavoriteUseCase
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.GetMovieUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _movieState = MutableStateFlow<UiState<MovieDetails>>(UiState.Loading)
    val movieState = _movieState.asStateFlow()

    private val _backgroundImage = MutableStateFlow<String?>("")
    val backgroundImage = _backgroundImage.asStateFlow()

    private val _moviesLists = MutableStateFlow<List<ListItem>>(emptyList())
    val moviesLists = _moviesLists.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getMovie(movieID: String) {
        _movieState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getMovieUseCase.invoke(movieID)) {
                is StatusResult.Error -> {
                    _movieState.value = UiState.Error(response.message)
                }
                is StatusResult.Success -> {
                    getMovieLists()
                    _backgroundImage.value = response.value.backdropPath
                    _movieState.value = UiState.Success(response.value)
                }
            }
        }
    }

    private fun getMovieLists() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    _moviesLists.value = result.value.results
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

    fun addFavorite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addFavoriteUseCase.execute(movieId)) {
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
