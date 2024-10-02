package com.proyect.movielists.presentation.screens.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.MovieFav
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.CreateMovieListUseCase
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.domine.usecase.RemoveMovieFromListUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListsViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val removeListUseCase: RemoveListUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _moviesLists = MutableStateFlow<List<ListItem>>(emptyList())
    val moviesLists = _moviesLists.asStateFlow()

    private val _favorites = MutableStateFlow<List<MovieFav>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init{
        getFavorites()
        getMovieLists()
    }

    private fun getFavorites() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFavoriteUseCase.invoke()) {
                is StatusResult.Success -> {
                    _favorites.value = result.value.results
                }
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private fun getMovieLists() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    _uiState.value = UIState.Success("")
                    _moviesLists.value = result.value.results
                }

                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    fun createMovieList(name: String, description: String, language: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createMovieListUseCase.execute(name, description, language)) {
                is StatusResult.Success -> {
                    getMovieLists()
                    _successMessage.value = "Lista creada exitosamente"
                }

                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    fun addMovieToList(listId: String, movieId: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addMovieToListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> _uiState.value = UIState.Success("Pelicula agregada exitosamente")
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    fun removeMovieFromList(listId: String, movieId: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeMovieFromListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> _uiState.value = UIState.Success("Pelicula agregada exitosamente")
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }

    }

    fun removeList(listId: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeListUseCase.execute(listId)) {
                is StatusResult.Success -> _uiState.value = UIState.Success("Pelicula agregada exitosamente")
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }

    }
}