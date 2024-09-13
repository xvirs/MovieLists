package com.proyect.movielists.data.mappers

import com.proyect.movielists.data.models.dto.AddMovieToListRequestDto
import com.proyect.movielists.data.models.dto.AddMovieToListResponseDto
import com.proyect.movielists.data.models.dto.AvatarDto
import com.proyect.movielists.data.models.dto.CreateMovieListRequestDto
import com.proyect.movielists.data.models.dto.CreateMovieListResponseDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.dto.RemoveListResponseDto
import com.proyect.movielists.data.models.dto.GetMovieListsResponseDto
import com.proyect.movielists.data.models.dto.GravatarDto
import com.proyect.movielists.data.models.dto.ListItemDto
import com.proyect.movielists.data.models.dto.LoginResponseDto
import com.proyect.movielists.data.models.dto.MovieDto
import com.proyect.movielists.data.models.dto.MoviesResponseDto
import com.proyect.movielists.data.models.dto.SessionTokenResponseDto
import com.proyect.movielists.data.models.dto.TmdbDto
import com.proyect.movielists.data.models.dto.UserProfileDto
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.Avatar
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.domine.models.Gravatar
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.domine.models.Tmdb
import com.proyect.movielists.domine.models.UserProfile

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        success = this.success,
        expires_at = this.expiresAt,
        request_token = this.requestToken
    )
}

fun SessionTokenResponseDto.toSessionTokenResponse(): SessionTokenResponse {
    return SessionTokenResponse(
        success = this.success,
        session_id = this.sessionId
    )
}

fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        avatar = this.avatar.toAvatar(),
        id = this.id,
        language = this.language,
        country = this.country,
        name = this.name,
        includeAdult = this.includeAdult,
        username = this.username
    )
}

fun AvatarDto.toAvatar(): Avatar {
    return Avatar(
        gravatar = this.gravatar.toGravatar(),
        tmdb = this.tmdb.toTmdb()
    )
}

fun GravatarDto.toGravatar(): Gravatar {
    return Gravatar(
        hash = this.hash
    )
}

fun TmdbDto.toTmdb(): Tmdb {
    return Tmdb(
        avatarPath = this.avatarPath
    )
}

fun MoviesResponseDto.toMovieListsResponse(): MoviesResponse {
    return MoviesResponse(
        page = this.page,
        results = this.results.map { it.toMovie() },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun CreateMovieListRequest.toDataModel(): CreateMovieListRequestDto {
    return CreateMovieListRequestDto(
        name = this.name,
        description = this.description,
        language = this.language
    )
}

fun CreateMovieListResponseDto.toDomainModel(): CreateMovieListResponse {
    return CreateMovieListResponse(
        success = this.success,
        statusCode = this.statusCode,
        statusMessage = this.statusMessage,
        listId = this.listId
    )
}

fun GetMovieListsResponseDto.toDomainModel(): GetMovieListsResponse {
    return GetMovieListsResponse(
        page = this.page,
        results = this.results.map { it.toDomainModel() },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}

fun ListItemDto.toDomainModel(): ListItem {
    return ListItem(
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

fun AddMovieToListRequest.toDataModel(): AddMovieToListRequestDto {
    return AddMovieToListRequestDto(
        mediaId = this.mediaId
    )
}

fun AddMovieToListResponseDto.toDomainModel(): AddMovieToListResponse {
    return AddMovieToListResponse(
        success = this.success,
        statusCode = this.statusCode,
        statusMessage = this.statusMessage
    )
}

fun RemoveMovieFromListRequest.toDataModel(): RemoveMovieFromListRequestDto {
    return RemoveMovieFromListRequestDto(
        mediaId = this.mediaId
    )
}

fun RemoveMovieFromListResponseDto.toDomainModel(): RemoveMovieFromListResponse {
    return RemoveMovieFromListResponse(
        success = this.success,
        statusCode = this.statusCode,
        statusMessage = this.statusMessage
    )
}

fun RemoveListResponseDto.toDomainModel(): RemoveListResponse {
    return RemoveListResponse(
        success = this.success,
        statusCode = this.statusCode,
        statusMessage = this.statusMessage
    )
}

