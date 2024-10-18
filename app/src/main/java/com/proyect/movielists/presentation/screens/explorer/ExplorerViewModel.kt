package com.proyect.movielists.presentation.screens.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyect.movielists.domine.usecase.AddFavoriteUseCase
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.GetFavoriteUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.MoviesUseCase
import com.proyect.movielists.domine.usecase.RemoveFavoriteUseCase
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.MovieUI
import com.proyect.movielists.presentation.models.mappers.toUIModel
import com.proyect.movielists.utils.MovieListType
import com.proyect.movielists.utils.StatusResult
import com.proyect.movielists.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExplorerViewModel(
    private val moviesUseCase: MoviesUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Loading)
    val uiState: StateFlow<UIState<String>> = _uiState

    private val _popularMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val popularMovies: StateFlow<List<MovieUI>> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val upcomingMovies: StateFlow<List<MovieUI>> = _upcomingMovies

    private val _nowPlayingMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val nowPlayingMovies: StateFlow<List<MovieUI>> = _nowPlayingMovies

    private val _topRatedMovies = MutableStateFlow<List<MovieUI>>(emptyList())
    val topRatedMovies: StateFlow<List<MovieUI>> = _topRatedMovies

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _movieLists = MutableStateFlow<List<ListItemUI>>(emptyList())
    val moviesLists = _movieLists
        .onStart { fetchMovieLists() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _successMessage = MutableStateFlow("")
    val successMessage = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchAllData()
    }

    private fun fetchAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading

            try {
                coroutineScope {
                    val popularMovies = async { fetchPopularMovies() }
                    val upcomingMovies = async { fetchUpcomingMovies() }
                    val nowPlayingMovies = async { fetchNowPlayingMovies() }
                    val topRatedMovies = async { fetchTopRatedMovies() }
                    val movieLists = async { fetchMovieLists() }

                    popularMovies.await()
                    upcomingMovies.await()
                    nowPlayingMovies.await()
                    topRatedMovies.await()
                    movieLists.await()
                }

                _uiState.value = UIState.Success("Datos cargados correctamente")
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error al cargar los datos")
            }
        }
    }

    private suspend fun fetchPopularMovies() {
        when (val result = moviesUseCase.execute(MovieListType.POPULAR)) {
            is StatusResult.Success -> {
                _popularMovies.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _popularMovies.value = emptyList()
                _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private suspend fun fetchUpcomingMovies() {
        when (val result = moviesUseCase.execute(MovieListType.UPCOMING)) {
            is StatusResult.Success -> {
                _upcomingMovies.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _upcomingMovies.value = emptyList()
                _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private suspend fun fetchNowPlayingMovies() {
        when (val result = moviesUseCase.execute(MovieListType.NOW_PLAYING)) {
            is StatusResult.Success -> {
                _nowPlayingMovies.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _nowPlayingMovies.value = emptyList()
                _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private suspend fun fetchTopRatedMovies() {
        when (val result = moviesUseCase.execute(MovieListType.TOP_RATED)) {
            is StatusResult.Success -> {
                _topRatedMovies.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _topRatedMovies.value = emptyList()
                _uiState.value = UIState.Error(result.message)
            }
        }
    }

    private suspend fun fetchMovieLists() {
        when (val result = getMovieListsUseCase.execute()) {
            is StatusResult.Success -> {
                _movieLists.value = result.value.results.map { it.toUIModel() }
            }
            is StatusResult.Error -> {
                _movieLists.value = emptyList()
                _uiState.value = UIState.Error(result.message)
            }
        }
    }

    fun addMovieToList(listId: String, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addMovieToListUseCase.execute(movieId, listId)) {
                is StatusResult.Success -> {
                    _successMessage.value = "Película agregada exitosamente"
                }
                is StatusResult.Error -> {
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun isFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val favorite = getFavoriteUseCase.invoke()) {
                is StatusResult.Success -> {
                    _isFavorite.value = favorite.value.results.any { it.id == movieId }
                }
                is StatusResult.Error -> {
                    _errorMessage.value = favorite.message
                }
            }
        }
    }

    fun addFavorite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addFavoriteUseCase.execute(movieId)) {
                is StatusResult.Success -> {
                    _isFavorite.value = true
                    _successMessage.value = "Película agregada exitosamente"
                }
                is StatusResult.Error -> {
                    _isFavorite.value = false
                    _errorMessage.value = result.message
                }
            }
        }
    }

    fun removeFavorite(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = removeFavoriteUseCase.execute(movieId)) {
                is StatusResult.Success -> {
                    _isFavorite.value = false
                    _successMessage.value = "Película eliminada de favoritos"
                }
                is StatusResult.Error -> {
                    _isFavorite.value = true
                    _errorMessage.value = result.message
                }
            }
        }
    }

}
