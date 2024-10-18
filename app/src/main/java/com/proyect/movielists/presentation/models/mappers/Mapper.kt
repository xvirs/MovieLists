package com.proyect.movielists.presentation.models.mappers

import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.domine.models.MovieFav
import com.proyect.movielists.domine.models.MovieItem
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.presentation.models.ListItemUI
import com.proyect.movielists.presentation.models.MovieDetailsUI
import com.proyect.movielists.presentation.models.MovieFavUI
import com.proyect.movielists.presentation.models.MovieListUI
import com.proyect.movielists.presentation.models.MovieUI
import com.proyect.movielists.presentation.models.ProfileUI

fun UserProfile.toUIModel(): ProfileUI {
    return ProfileUI(
        avatarPath = this.avatar.tmdb.avatarPath,
        name = this.name,
        username = this.username,
        country = this.country
    )
}

fun Movie.toUIModel(): MovieUI {
    return MovieUI(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = this.backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
    )
}

fun MovieFav.toUIModel(): MovieFavUI {
    return MovieFavUI(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterUrl,
        releaseDate = this.releaseDate,
        isFavorite = true,
        voteAverage = this.voteAverage
    )
}

fun ListItem.toUIModel(): ListItemUI {
    return ListItemUI(
        description = this.description,
        favoriteCount = this.favoriteCount,
        id = this.id,
        itemCount = this.itemCount,
        iso6391 = this.iso6391,
        listType = this.listType,
        name = this.name,
        posterPath = this.posterPath
    )
}

fun GetMovieListResponse.toMovieListUI(): MovieListUI {
    return MovieListUI(
        id = this.id,
        name = this.name,
        description = this.description,
        movies = this.movies.map { it.toMovieUI() } // Reutilizando MovieUI
    )
}

fun MovieItem.toMovieUI(): MovieUI {
    return MovieUI(
        id = this.id,
        title = this.title ?: "Sin título",
        overview = this.overview ?: "Sin descripción",
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = null,
        releaseDate = null,
        voteAverage = null
    )
}

fun MovieDetails.toUIModel(): MovieDetailsUI {
    return MovieDetailsUI(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = this.backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        runtime = this.runtime,
        genres = this.genres.map { it.name },  // Mapeando los géneros a una lista de nombres
        tagline = this.tagline,
        status = this.status
    )
}