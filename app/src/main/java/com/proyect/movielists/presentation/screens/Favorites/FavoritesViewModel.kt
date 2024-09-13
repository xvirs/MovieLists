package com.proyect.movielists.presentation.screens.Favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(

) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

//    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
//    val taskList: StateFlow<List<Task>> = _taskList

    init {
        loadSome()
    }

    private fun loadSome() {
        viewModelScope.launch {
            setLoading(true)
//            _taskList.value = getTasksUseCase()
            setLoading(false)
        }
    }


    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}