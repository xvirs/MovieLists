package com.proyect.movielists.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.AddFavoriteUseCase
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.CreateMovieListUseCase
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.GetMovieUseCase
import com.proyect.movielists.domine.usecase.RemoveFavoriteUseCase
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val createMovieListUseCase: CreateMovieListUseCase,
) : ViewModel() {

    private val _moviesLists = MutableStateFlow<List<ListItemUI>>(emptyList())
    val moviesLists = _moviesLists
        .onStart { getMovieLists() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _movieDetail = MutableStateFlow<UIState<MovieDetailsUI>>(UIState.Loading)
    val movieDetail = _movieDetail.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

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
                    _movieDetail.value = UIState.Success(response.value.toUIModel())
                }
            }
        }
    }

    fun isFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val favorite = getFavoriteUseCase.invoke()) {
                is StatusResult.Success -> {
                    _isFavorite.value = favorite.value.results.any { it.id == movieId }
                }
                is StatusResult.Error -> {
                    _errorMessage.value = favorite.message
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

    fun createMovieList(name: String, description: String, language: String, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createMovieListUseCase.execute(name, description, language)) {
                is StatusResult.Success -> {
                    addMovieToList(result.value.listId.toString(), movieId)
                }
                is StatusResult.Error -> result.message
            }
        }
    }

    fun addFavorite(movieId: Int){
        _isFavorite.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addFavoriteUseCase.execute(movieId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Pelicula agregada exitosamente"
                }
                is StatusResult.Error -> {
                    _isFavorite.value = false
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun removeFavorite(movieId: Int){
        _isFavorite.value = false
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeFavoriteUseCase.execute(movieId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Pelicula agregada exitosamente"
                }
                is StatusResult.Error -> {
                    _isFavorite.value = true
                    _errorMessage.value = result.message
                }
            }
        }
    }
}