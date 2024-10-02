package com.proyect.movielists.presentation.components.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.domine.usecase.ProfileUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<UserProfile>>(UIState.Idle)
    val uiState: StateFlow<UIState<UserProfile>> = _uiState.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = profileUseCase.executeAuthenticatedRequest()) {
                is StatusResult.Success -> {
                    _uiState.value = UIState.Success(result.value)
                }
                is StatusResult.Error -> {
                    _uiState.value = UIState.Error(result.message)
                }
            }
        }
    }
}
