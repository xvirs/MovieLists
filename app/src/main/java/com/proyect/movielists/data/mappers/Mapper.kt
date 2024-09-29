package com.proyect.movielists.data.mappers

import com.proyect.movielists.data.models.dto.AddMovieToListRequestDto
import com.proyect.movielists.data.models.dto.AddMovieToListResponseDto
import com.proyect.movielists.data.models.dto.AvatarDto
import com.proyect.movielists.data.models.dto.CollectionInfoDto
import com.proyect.movielists.data.models.dto.CreateMovieListRequestDto
import com.proyect.movielists.data.models.dto.CreateMovieListResponseDto
import com.proyect.movielists.data.models.dto.GenreDto
import com.proyect.movielists.data.models.dto.GetMovieListResponseDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.dto.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.dto.RemoveListResponseDto
import com.proyect.movielists.data.models.dto.GetMovieListsResponseDto
import com.proyect.movielists.data.models.dto.GravatarDto
import com.proyect.movielists.data.models.dto.ListItemDto
import com.proyect.movielists.data.models.dto.LoginResponseDto
import com.proyect.movielists.data.models.dto.MovieDetailsDto
import com.proyect.movielists.data.models.dto.MovieDto
import com.proyect.movielists.data.models.dto.MovieItemDto
import com.proyect.movielists.data.models.dto.MoviesResponseDto
import com.proyect.movielists.data.models.dto.ProductionCompanyDto
import com.proyect.movielists.data.models.dto.ProductionCountryDto
import com.proyect.movielists.data.models.dto.SessionTokenResponseDto
import com.proyect.movielists.data.models.dto.SpokenLanguageDto
import com.proyect.movielists.data.models.dto.TmdbDto
import com.proyect.movielists.data.models.dto.UserProfileDto
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.Avatar
import com.proyect.movielists.domine.models.CollectionInfo
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.Genre
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.domine.models.Gravatar
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.LoginResponse
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.domine.models.MovieItem
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.models.ProductionCompany
import com.proyect.movielists.domine.models.ProductionCountry
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.domine.models.SpokenLanguage
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

fun GetMovieListResponseDto.toDomainModel(): GetMovieListResponse {
    return GetMovieListResponse(
        createdBy = this.createdBy,
        description = this.description,
        favoriteCount = this.favoriteCount,
        id = this.id,
        language = this.iso6391,
        itemCount = this.itemCount,
        movies = this.items.map { it.toDomain() },
        name = this.name,
        page = this.page,
        posterPath = this.posterPath,
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}

fun MovieItemDto.toDomain(): MovieItem {
    return MovieItem(
        id = this.id,
        title = this.title,
        overview = this.overview,
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

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        adult = this.adult,
        backdropPath = this.backdropPath,
        belongsToCollection = this.belongsToCollection?.toCollectionInfo(),
        budget = this.budget,
        genres = this.genres.map { it.toGenre() },
        homepage = this.homepage,
        id = this.id,
        imdbId = this.imdbId,
        originCountry = this.originCountry,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = this.productionCompanies.map { it.toProductionCompany() },
        productionCountries = this.productionCountries.map { it.toProductionCountry() },
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spokenLanguages.map { it.toSpokenLanguage() },
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

fun ProductionCompanyDto.toProductionCompany(): ProductionCompany {
    return ProductionCompany(
        id = this.id,
        logoPath = this.logoPath,
        name = this.name,
        originCountry = this.originCountry
    )
}

fun ProductionCountryDto.toProductionCountry(): ProductionCountry {
    return ProductionCountry(
        iso = this.iso,
        name = this.name
    )
}

fun SpokenLanguageDto.toSpokenLanguage(): SpokenLanguage {
    return SpokenLanguage(
        englishName = this.englishName,
        iso = this.iso,
        name = this.name
    )
}

fun CollectionInfoDto.toCollectionInfo(): CollectionInfo {
    return CollectionInfo(
        id = this.id,
        name = this.name,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath
    )
}
