package com.proyect.movielists.presentation.screens.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.CreateMovieListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.mappers.toMovieListUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListsViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val removeListUseCase: RemoveListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _moviesLists = MutableStateFlow<List<ListItemUI>>(emptyList())
    val moviesLists = _moviesLists
        .onStart { observeMovieLists() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        observeMovieLists()
    }

    private fun observeMovieLists() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getMovieListsUseCase.executeFlow()
                .collect { result ->
                    when (result) {
                        is StatusResult.Success -> {
                            _moviesLists.value = result.value.results.map { listItem ->
                                val posterUrls = getPosterUrlsForList(listItem.id)
                                listItem.toUIModel().copy(posterUrls = posterUrls)
                            }
                        }
                        is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
                    }
                    _uiState.value = UIState.Success("")
                }
        }
    }

    suspend fun createMovieList(name: String, description: String, language: String) : String {
        val result = withContext(Dispatchers.IO) {
            createMovieListUseCase.execute(name, description, language)
        }
        observeMovieLists()
        return when (result) {
            is StatusResult.Success -> " :)   Creaste la Lista $name"
            is StatusResult.Error -> ":o   No se pudo crear la Lista $name"
            }
    }

    private suspend fun getPosterUrlsForList(listId: Int): List<String> {
        return when (val result = getMovieListUseCase.execute(listId.toString())) {
            is StatusResult.Success -> result.value.toMovieListUI().movies.mapNotNull { it.posterUrl }
            is StatusResult.Error -> emptyList()
        }
    }

    suspend fun removeList(listId: Int, listName: String): String {
        val result = withContext(Dispatchers.IO) {
            removeListUseCase.execute(listId)
        }
        return when (result) {
            is StatusResult.Success -> ":(   Eliminaste la lista $listName"
            is StatusResult.Error -> ":o   No se pudo eliminar la lista $listName"
        }
    }
}