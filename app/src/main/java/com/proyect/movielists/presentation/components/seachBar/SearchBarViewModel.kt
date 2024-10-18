package com.proyect.movielists.presentation.components.seachBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.SearchMoviesUseCase
import com.proyect.movielists.presentation.models.MovieUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce

class SearchBarViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _listMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val listMovies: StateFlow<List<MovieUI>> = _listMovies.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery
                .debounce(300L)
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        searchMovies(query)
                    } else {
                        _listMovies.value = emptyList()
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private suspend fun searchMovies(search: String) {
        when (val result = searchMoviesUseCase.execute(search)) {
            is StatusResult.Success -> {
                _listMovies.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _listMovies.value = emptyList()
            }
        }
    }
}