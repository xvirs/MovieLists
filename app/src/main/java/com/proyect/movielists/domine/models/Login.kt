package com.proyect.movielists.domine.models

data class LoginRequest(
    val username: String,
    val password: String,
    val request_token: String
)

data class SessionTokenRequest(
    val request_token: String
)

data class LoginResponse (
    val success: Boolean? = null,
    val expires_at: String? = null,
    val request_token: String? = null
)

data class SessionTokenResponse (
    val success: Boolean? = null,
    val session_id: String? = null
)