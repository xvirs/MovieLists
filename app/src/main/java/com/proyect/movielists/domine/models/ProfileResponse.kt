package com.proyect.movielists.domine.models

data class UserProfile(
    val avatar: Avatar,
    val id: Int,
    val language: String,
    val country: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)

data class Avatar(
    val gravatar: Gravatar,
    val tmdb: Tmdb
)

data class Gravatar(
    val hash: String
)

data class Tmdb(
    val avatarPath: String?
)
