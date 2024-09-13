package com.proyect.movielists.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.proyect.movielists.domine.usecase.CreateSessionUseCase
import com.proyect.movielists.domine.usecase.RequestTokenUseCase
import com.proyect.movielists.domine.usecase.ValidateLoginUseCase
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val requestTokenUseCase: RequestTokenUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginOK = MutableStateFlow(false)
    val loginOK = _loginOK.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            try {
                requestToken()
                validateLogin(email, password)
                createSession()


            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error al iniciar sesion"
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
            is StatusResult.Success -> Unit // Success, continuar con la siguiente fase
            is StatusResult.Error -> throw Exception(result.message)
        }
    }

    private suspend fun createSession(): String {
        return when (val result = createSessionUseCase.invoke()) {
            is StatusResult.Success -> {
                _loginOK.value = true
                result.value.session_id ?: throw Exception("Session ID not found")
            }
            is StatusResult.Error -> throw Exception(result.message)
        }
    }

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}

