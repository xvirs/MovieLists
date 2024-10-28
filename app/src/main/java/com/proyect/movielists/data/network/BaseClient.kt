package com.proyect.movielists.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BaseClient {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3"
        private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMzRlNTQyZGRhOWJmYzBkMjc3ZTRmNzNlZGRmZWFlZSIsIm5iZiI6MTcyNTgwOTIzNi4wNTk0MTksInN1YiI6IjYyNDM2NDllODExNDBmMDA5MDllYjBkZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DQiu9d57fSf8oaUFjvuzXzY2YfQcKGEYmVfEVj2_xUE"
    }

    private val apiClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
            })
        }
    }

    internal suspend fun post(
        url: String,
        body: String? = null,
        sessionId: String? = null,
        errorMessage: String,
    ): HttpStatus {
        return try {
            val response = apiClient.post("$BASE_URL$url") {
                contentType(ContentType.Application.Json)
                body?.let {
                    setBody(body)
                }
                header("Authorization", "Bearer $BEARER_TOKEN")
                sessionId?.let {
                    parameter("session_id", it)
                }
            }
            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = "Error desconocido: ${e.message}")
        }
    }

    internal suspend fun get(
        url: String,
        body: String? = null,
        valueParams: Map<String, String>? = null,
        language: String? = null,
        errorMessage: String,
    ): HttpStatus {
        return try {
            val response = apiClient.get(BASE_URL + url) {
                contentType(ContentType.Application.Json)
                body?.let {
                    setBody(body)
                }
                header("Authorization", "Bearer $BEARER_TOKEN")

                valueParams?.forEach { (key, value) ->
                    parameter(key, value)
                }

                language?.let {
                    parameter("language", it)
                }
            }

            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = "Ups! Atrapaste un error desconocido, vuelve a intentarlo")
        }
    }

    internal suspend fun delete(
        url: String,
        sessionId: String? = null,
        body: String? = null,
        errorMessage: String
    ): HttpStatus {
        return try {
            val response = apiClient.delete(BASE_URL + url) {
                contentType(ContentType.Application.Json)
                body?.let {
                    setBody(body)
                }
                header("Authorization", "Bearer $BEARER_TOKEN")
                sessionId?.let {
                    parameter("session_id", it)
                }
            }
            if (response.status.value in 200..299) {
                HttpStatus(httpResponse = response)
            } else {
                HttpStatus(errorMessage = errorMessage)
            }
        } catch (e: Exception) {
            HttpStatus(errorMessage = "Error desconocido: ${e.message}")
        }
    }

}

internal data class HttpStatus(
    val httpResponse: HttpResponse? = null,
    val errorMessage: String = "",
    val errorType: ErrorType? = null
)

enum class ErrorType {
    NETWORK,
    SERVER
}