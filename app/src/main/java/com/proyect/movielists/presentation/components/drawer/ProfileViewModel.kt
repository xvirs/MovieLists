package com.proyect.movielists.presentation.components.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.domine.usecase.ProfileUseCase
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile = _profile.asStateFlow()


    init {
        getProfile()
    }

    private fun getProfile() {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = profileUseCase.executeAuthenticatedRequest()) {
                is StatusResult.Success -> {
                    _profile.value = result.value
                    setLoading(false)
                }
                is StatusResult.Error -> {
                    _profile.value = null
                    setLoading(false)
                }
            }
        }
    }

    private fun setLoading(value: Boolean){
        _isLoading.value = value
    }
}