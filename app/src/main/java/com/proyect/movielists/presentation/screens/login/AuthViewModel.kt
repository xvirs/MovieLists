package com.proyect.movielists.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.proyect.movielists.domine.usecase.CreateSessionUseCase
import com.proyect.movielists.domine.usecase.RequestTokenUseCase
import com.proyect.movielists.domine.usecase.ValidateLoginUseCase
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            try {
                val token = requestToken()
                validateLogin(email, password)
                val sessionId = createSession()
                _uiState.value = UIState.Success(sessionId)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error al iniciar sesion")
            }
        }
    }

    private suspend fun requestToken(): String {
        return when (val result = requestTokenUseCase.invoke()) {
            is StatusResult.Success -> result.value.request_token!!
            is StatusResult.Error -> throw Exception(result.message)
        }
    }

    private suspend fun validateLogin(email: String, password: String) {
        when (val result = validateLoginUseCase.invoke(email, password)) {
            is StatusResult.Success -> Unit
            is StatusResult.Error -> throw Exception(result.message)
        }
    }

    private suspend fun createSession(): String {
        return when (val result = createSessionUseCase.invoke()) {
            is StatusResult.Success -> result.value.session_id ?: throw Exception("Session ID not found")
            is StatusResult.Error -> throw Exception(result.message)
        }
    }

}

