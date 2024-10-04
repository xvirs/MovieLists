package com.proyect.movielists.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.AddFavoriteUseCase
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.GetMovieUseCase
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
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

    private val _movieDetail = MutableStateFlow<UIState<MovieDetailsUI>>(UIState.Loading)
    val movieDetail = _movieDetail.asStateFlow()

    private val _backgroundImage = MutableStateFlow<String?>("")
    val backgroundImage = _backgroundImage.asStateFlow()

    private val _moviesLists = MutableStateFlow<List<ListItemUI>>(emptyList())
    val moviesLists = _moviesLists.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun getMovie(movieID: String) {
        _movieDetail.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getMovieUseCase.invoke(movieID)) {
                is StatusResult.Error -> {
                    _movieDetail.value = UIState.Error(response.message)
                }
                is StatusResult.Success -> {
                    getMovieLists()
                    _backgroundImage.value = response.value.backdropPath
                    _movieDetail.value = UIState.Success(response.value.toUIModel())
                }
            }
        }
    }

    private fun getMovieLists() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    _moviesLists.value = result.value.results.map { it.toUIModel() }
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