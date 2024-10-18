package com.proyect.movielists.presentation.screens.dashboard

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

class DashboardViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _favorites = MutableStateFlow<List<MovieFavUI>>(emptyList())
    val favorites = _favorites
        .onStart { getFavorites() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    private fun getFavorites() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFavoriteUseCase.invoke()) {
                is StatusResult.Success -> {
                    _uiState.value = UIState.Success("")
                    _favorites.value = UIState.Success(result.value.results.map { it.toUIModel() }).data
                }
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }
}
