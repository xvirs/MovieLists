package com.proyect.movielists.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.proyect.movielists.domine.usecase.LoginUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isBlank() || password.isBlank()) {
                _uiState.value = UIState.Error("Por favor, complete todos los campos.")
                return@launch
            }

            _uiState.value = UIState.Loading
            when (val result = loginUseCase.invoke(email, password)) {
                is StatusResult.Success -> _uiState.value = UIState.Success("Se inició sesión correctamente")
                is StatusResult.Error -> _uiState.value = UIState.Error(result.message)
            }
        }
    }
}
