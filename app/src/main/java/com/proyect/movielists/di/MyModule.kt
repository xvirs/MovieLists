package com.proyect.movielists.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.proyect.movielists.presentation.screens.login.AuthViewModel
import com.proyect.movielists.presentation.components.drawer.ProfileViewModel
import com.proyect.movielists.presentation.screens.dashboard.DashboardViewModel
import com.proyect.movielists.data.datasource.AuthDataSourceImpl
import com.proyect.movielists.data.datasource.MovieDataSourceImpl
import com.proyect.movielists.data.datasource.MovieListDataSourceImpl
import com.proyect.movielists.data.datasource.MoviesDataSourceImpl
import com.proyect.movielists.data.datasource.ProfileDataSourceImpl
import com.proyect.movielists.data.datasource.SessionDataStoreImpl
import com.proyect.movielists.data.interfaces.AuthDataSource
import com.proyect.movielists.data.interfaces.MovieDataSource
import com.proyect.movielists.data.interfaces.MovieListDataSource
import com.proyect.movielists.data.interfaces.MoviesDataSource
import com.proyect.movielists.data.interfaces.ProfileDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.network.BaseClient
import com.proyect.movielists.data.repository.AuthRepositoryImpl
import com.proyect.movielists.data.repository.MovieListRepositoryImpl
import com.proyect.movielists.data.repository.MovieRepositoryImpl
import com.proyect.movielists.data.repository.MoviesRepositoryImpl
import com.proyect.movielists.data.repository.ProfileRepositoryImpl
import com.proyect.movielists.domine.interfaces.AuthRepository
import com.proyect.movielists.domine.interfaces.MovieListRepository
import com.proyect.movielists.domine.interfaces.MovieRepository
import com.proyect.movielists.domine.interfaces.MoviesRepository
import com.proyect.movielists.domine.interfaces.ProfileRepository
import com.proyect.movielists.domine.usecase.AddMovieToListUseCase
import com.proyect.movielists.domine.usecase.CreateMovieListUseCase
import com.proyect.movielists.domine.usecase.CreateSessionUseCase
import com.proyect.movielists.domine.usecase.GetMovieListUseCase
import com.proyect.movielists.domine.usecase.RemoveMovieFromListUseCase
import com.proyect.movielists.domine.usecase.RemoveListUseCase
import com.proyect.movielists.domine.usecase.GetMovieListsUseCase
import com.proyect.movielists.domine.usecase.GetMovieUseCase
import com.proyect.movielists.domine.usecase.MoviesUseCase
import com.proyect.movielists.domine.usecase.ProfileUseCase
import com.proyect.movielists.domine.usecase.RequestTokenUseCase
import com.proyect.movielists.domine.usecase.SearchMoviesUseCase
import com.proyect.movielists.domine.usecase.ValidateLoginUseCase
import com.proyect.movielists.presentation.components.seachBar.SearchBarViewModel
import com.proyect.movielists.presentation.screens.Favorites.FavoritesViewModel
import com.proyect.movielists.presentation.screens.list.ListViewModel
import com.proyect.movielists.presentation.screens.lists.ListsViewModel
import com.proyect.movielists.presentation.screens.movie.MovieViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val useCaseModule = module {
    single { BaseClient() }
    single { createDataStore(get()) }
    single <SessionDataStore> { SessionDataStoreImpl(get()) }

    single<AuthDataSource> { AuthDataSourceImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { CreateSessionUseCase(get()) }
    factory { RequestTokenUseCase(get()) }
    factory { ValidateLoginUseCase(get()) }

    single<ProfileDataSource> { ProfileDataSourceImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    factory { ProfileUseCase(get()) }

    single<MoviesDataSource> { MoviesDataSourceImpl(get()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
    factory { MoviesUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }

    single<MovieListDataSource> { MovieListDataSourceImpl(get()) }
    single<MovieListRepository> { MovieListRepositoryImpl(get(), get()) }
    factory { CreateMovieListUseCase(get()) }
    factory { GetMovieListsUseCase(get()) }
    factory { AddMovieToListUseCase(get()) }
    factory { RemoveMovieFromListUseCase(get()) }
    factory { RemoveListUseCase(get()) }
    factory { GetMovieListUseCase(get()) }

    single<MovieDataSource> { MovieDataSourceImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    factory { GetMovieUseCase(get()) }

}

val viewModelModule = module {
    viewModel { AuthViewModel(get(),get(),get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { FavoritesViewModel() }
    viewModel { ListsViewModel(get(),get(),get(),get(),get()) }
    viewModel { ListViewModel(get(), get(), get(), get()) }
    viewModel { SearchBarViewModel(get()) }
    viewModel { MovieViewModel(get(), get(), get()) }
}


fun createDataStore(context: Context): DataStore<Preferences> {
    return  PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile("session_prefs") }
    )
}