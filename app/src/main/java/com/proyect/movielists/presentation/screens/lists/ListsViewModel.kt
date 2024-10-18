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
        .onStart { getMovieLists() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        _uiState.value = UIState.Loading
    }

    fun getMovieLists() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListsUseCase.execute()) {
                is StatusResult.Success -> {
                    val lists = result.value.results.map { listItem ->
                        val posterUrls = getPosterUrlsForList(listItem.id)
                        listItem.toUIModel().copy(posterUrls = posterUrls)
                    }
                    _moviesLists.value = lists
                    _uiState.value = UIState.Success("")
                }
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private suspend fun getPosterUrlsForList(listId: Int): List<String> {
        return when (val result = getMovieListUseCase.execute(listId.toString())) {
            is StatusResult.Success -> result.value.toMovieListUI().movies.mapNotNull { it.posterUrl }
            is StatusResult.Error -> emptyList()
        }
    }

    fun createMovieList(name: String, description: String, language: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createMovieListUseCase.execute(name, description, language)) {
                is StatusResult.Success -> {
                    getMovieLists()
                }
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }

    suspend fun removeList(listId: Int, listName: String) : String  {
        val result = withContext(Dispatchers.IO) {
            removeListUseCase.execute(listId)
        }
        when (result) {
            is StatusResult.Success -> {
                return " :(   Eliminaste la lista $listName"
            }
            is StatusResult.Error -> {
                return " :o   No se pudo eliminar la lista $listName"
            }
        }
    }
}