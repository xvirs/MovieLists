package com.proyect.movielists.utils

enum class MovieListType(val endpoint: String) {
    POPULAR("popular"),
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing"),
    TOP_RATED("top_rated")
}
