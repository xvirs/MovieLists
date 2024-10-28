package com.proyect.movielists.presentation.components.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.DeleteSessionUseCase
import com.proyect.movielists.domine.usecase.ProfileUseCase
import com.proyect.movielists.presentation.models.ProfileUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val profileUseCase: ProfileUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<ProfileUI>>(UIState.Idle)
    val uiState: StateFlow<UIState<ProfileUI>> = _uiState.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = profileUseCase.executeAuthenticatedRequest()) {
                is StatusResult.Success -> {
                    _uiState.value = UIState.Success(result.value.toUIModel())
                }
                is StatusResult.Error -> {
                    _uiState.value = UIState.Error(result.message)
                }
            }
        }
    }

    suspend fun deleteSession() : String {
        _uiState.value = UIState.Loading
        val result = withContext(Dispatchers.IO) {
            deleteSessionUseCase.invoke()
        }
        return when (result) {
            is StatusResult.Success -> {
                ":)  Hasta Luego!"
            }
            is StatusResult.Error -> ":(  Hubo un error al cerrar la sesion"
        }

    }
}