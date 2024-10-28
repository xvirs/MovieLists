package com.proyect.movielists.presentation.screens.watched

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.presentation.models.MovieFavUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchedViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _watched = MutableStateFlow<List<MovieFavUI>>(emptyList())
    val watched = _watched
        .onStart { getFavorites() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun getFavorites() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteUseCase.invokeFlow()
                .collect { result ->
                    when (result) {
                        is StatusResult.Success -> {
                            _watched.value = result.value.results.map { it.toUIModel() }
                            _uiState.value = UIState.Success("")
                        }
                        is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
                    }
                }
        }
    }
}
