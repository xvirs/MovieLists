package com.proyect.movielists.presentation.components.seachBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.usecase.SearchMoviesUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchBarViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Movie>>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<Movie>>> = _uiState.asStateFlow()

    fun searchMovies(search: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchMoviesUseCase.execute(search)) {
                is StatusResult.Success -> {
                    _uiState.value = UIState.Success(result.value.results)
                }
                is StatusResult.Error -> {
                    _uiState.value = UIState.Error(result.message)
                }
            }
        }
    }
}