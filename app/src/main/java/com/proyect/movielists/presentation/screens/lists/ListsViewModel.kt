package com.proyect.movielists.presentation.screens.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.CreateMovieListUseCase
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.domine.usecase.RemoveMovieFromListUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.utils.StatusResult
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



    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _moviesLists = MutableStateFlow<List<ListItem>>(emptyList())
    val moviesLists = _moviesLists.asStateFlow()

    private val _favorites = MutableStateFlow<MovieFavResponse?>(null)
    val favorites = _favorites.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init{
        getFavorites()
        getMovieLists()
    }

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    private fun getFavorites() {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFavoriteUseCase.invoke()) {
                is StatusResult.Success -> {
                    setLoading(false)
                    _favorites.value = result.value
                }
                is StatusResult.Error -> {
                    setLoading(false)
                    _errorMessage.value = result.message
                }
            }
        }
    }

    private fun getMovieLists() {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    setLoading(false)
                    _moviesLists.value = result.value.results
                }

                is StatusResult.Error -> {
                    setLoading(false)
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun createMovieList(name: String, description: String, language: String) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createMovieListUseCase.execute(name, description, language)) {
                is StatusResult.Success -> {
                    setLoading(false)
                    getMovieLists()
                    _successMessage.value = "Lista creada exitosamente"
                }

                is StatusResult.Error -> {
                    setLoading(false)
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun addMovieToList(listId: String, movieId: Int) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addMovieToListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    setLoading(false)
                    _successMessage.value = "Pelicula agregada exitosamente"
                }

                is StatusResult.Error -> {
                    setLoading(false)
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun removeMovieFromList(listId: String, movieId: String) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeMovieFromListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    setLoading(false)
                    _successMessage.value = "Pelicula eliminada exitosamente"
                }
                is StatusResult.Error -> {
                    setLoading(false)
                    _errorMessage.value = result.message
                }

            }
        }

    }

    fun removeList(listId: Int) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeListUseCase.execute(listId)) {
                is StatusResult.Success -> {
                    getMovieLists()
                    setLoading(false)
                    _successMessage.value = "Lista eliminada exitosamente"
                }

                is StatusResult.Error -> {
                    getMovieLists()
                    setLoading(false)
                    _errorMessage.value = result.message
                }
            }
        }

    }

}