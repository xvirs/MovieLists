package com.proyect.movielists.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto (
    val success: Boolean? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("request_token") val requestToken: String
)


@Serializable
data class SessionTokenResponseDto (
    val success: Boolean? = null,
    @SerialName("session_id") val sessionId: String? = null
)

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String,
    @SerialName("request_token") val requestToken: String
)

@Serializable
data class SessionTokenRequestDto(
    @SerialName("request_token") val requestToken: String
)