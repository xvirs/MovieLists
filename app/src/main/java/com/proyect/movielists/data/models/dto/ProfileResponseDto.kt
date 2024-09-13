package com.proyect.movielists.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val avatar: AvatarDto,
    val id: Int,
    @SerialName("iso_639_1") val language: String,
    @SerialName("iso_3166_1") val country: String,
    val name: String,
    @SerialName("include_adult") val includeAdult: Boolean,
    val username: String
)

@Serializable
data class AvatarDto(
    val gravatar: GravatarDto,
    val tmdb: TmdbDto
)

@Serializable
data class GravatarDto(
    val hash: String
)

@Serializable
data class TmdbDto(
    @SerialName("avatar_path") val avatarPath: String?
)
