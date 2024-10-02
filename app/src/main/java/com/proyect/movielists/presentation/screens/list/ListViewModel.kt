package com.proyect.movielists.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.usecase.GetMovieListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.RemoveMovieFromListUseCase
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val removeMovieFromListUseCase: RemoveMovieFromListUseCase,
    private val removeListUseCase: RemoveListUseCase,
    private val getMovieListUseCase : GetMovieListUseCase
) : ViewModel() {

    private val _listDetailsState = MutableStateFlow<UIState<GetMovieListResponse>>(UIState.Loading)
    val listDetailsState = _listDetailsState.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _listMovies = MutableStateFlow<List<ListItem>>(emptyList())
    val listMovies = _listMovies.asStateFlow()

    fun getListDetails( listId: String) {
        _listDetailsState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMovieListUseCase.execute(listId)) {
                is StatusResult.Success -> {
                    _listDetailsState.value = UIState.Success(result.value)
                }
                is StatusResult.Error -> {
                    _listDetailsState.value = UIState.Error(result.message)
                }
            }
        }
    }

    fun removeMovieFromList(listId: String, movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeMovieFromListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Película eliminada exitosamente"
                    getListDetails(listId)
                }
                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun removeList(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeListUseCase.execute(listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Lista eliminada exitosamente"
                }
                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }
}
