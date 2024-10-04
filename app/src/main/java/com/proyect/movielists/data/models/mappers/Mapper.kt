package com.proyect.movielists.data.models.mappers

import com.proyect.movielists.data.models.AddMovieToListRequestDto
import com.proyect.movielists.data.models.AddMovieToListResponseDto
import com.proyect.movielists.data.models.AvatarDto
import com.proyect.movielists.data.models.CollectionInfoDto
import com.proyect.movielists.data.models.CreateMovieListRequestDto
import com.proyect.movielists.data.models.CreateMovieListResponseDto
import com.proyect.movielists.data.models.FavoriteRequestDTO
import com.proyect.movielists.data.models.FavoriteResponseDTO
import com.proyect.movielists.data.models.GenreDto
import com.proyect.movielists.data.models.GetMovieListResponseDto
import com.proyect.movielists.data.models.RemoveMovieFromListRequestDto
import com.proyect.movielists.data.models.RemoveMovieFromListResponseDto
import com.proyect.movielists.data.models.RemoveListResponseDto
import com.proyect.movielists.data.models.GetMovieListsResponseDto
import com.proyect.movielists.data.models.GravatarDto
import com.proyect.movielists.data.models.ListItemDto
import com.proyect.movielists.data.models.MovieDetailsDto
import com.proyect.movielists.data.models.MovieDto
import com.proyect.movielists.data.models.MovieFavDTO
import com.proyect.movielists.data.models.MovieFavResponseDTO
import com.proyect.movielists.data.models.MovieItemDto
import com.proyect.movielists.data.models.MoviesResponseDto
import com.proyect.movielists.data.models.ProductionCompanyDto
import com.proyect.movielists.data.models.ProductionCountryDto
import com.proyect.movielists.data.models.SessionTokenResponseDto
import com.proyect.movielists.data.models.SpokenLanguageDto
import com.proyect.movielists.data.models.TmdbDto
import com.proyect.movielists.data.models.UserProfileDto
import com.proyect.movielists.domine.models.AddMovieToListRequest
import com.proyect.movielists.domine.models.AddMovieToListResponse
import com.proyect.movielists.domine.models.Avatar
import com.proyect.movielists.domine.models.CollectionInfo
import com.proyect.movielists.domine.models.CreateMovieListRequest
import com.proyect.movielists.domine.models.CreateMovieListResponse
import com.proyect.movielists.domine.models.FavoriteRequest
import com.proyect.movielists.domine.models.FavoriteResponse
import com.proyect.movielists.domine.models.Genre
import com.proyect.movielists.domine.models.GetMovieListResponse
import com.proyect.movielists.domine.models.RemoveMovieFromListRequest
import com.proyect.movielists.domine.models.RemoveMovieFromListResponse
import com.proyect.movielists.domine.models.RemoveListResponse
import com.proyect.movielists.domine.models.GetMovieListsResponse
import com.proyect.movielists.domine.models.Gravatar
import com.proyect.movielists.domine.models.ListItem
import com.proyect.movielists.domine.models.Movie
import com.proyect.movielists.domine.models.MovieDetails
import com.proyect.movielists.domine.models.MovieFav
import com.proyect.movielists.domine.models.MovieFavResponse
import com.proyect.movielists.domine.models.MovieItem
import com.proyect.movielists.domine.models.MoviesResponse
import com.proyect.movielists.domine.models.ProductionCompany
import com.proyect.movielists.domine.models.ProductionCountry
import com.proyect.movielists.domine.models.SessionTokenResponse
import com.proyect.movielists.domine.models.SpokenLanguage
import com.proyect.movielists.domine.models.Tmdb
import com.proyect.movielists.domine.models.UserProfile


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

fun FavoriteRequestDTO.toDomain(): FavoriteRequest {
    return FavoriteRequest(
        mediaType = mediaType,
        mediaId = mediaId,
        isFavorite = favorite
    )
}

fun FavoriteRequest.toData(): FavoriteRequestDTO {
    return FavoriteRequestDTO(
        mediaType = mediaType,
        mediaId = mediaId,
        favorite = isFavorite
    )
}


fun FavoriteResponseDTO.toDomain(): FavoriteResponse {
    return FavoriteResponse(
        isSuccess = success,
        code = statusCode,
        message = statusMessage
    )
}

fun MovieFavDTO.toDomain(): MovieFav {
    return MovieFav(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        adult = adult,
        video = video,
        originalLanguage = originalLanguage,
        genreIds = genreIds
    )
}

fun MovieFavResponseDTO.toDomain(): MovieFavResponse {
    return MovieFavResponse(
        page = page,
        results = results.map { it.toDomain() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}