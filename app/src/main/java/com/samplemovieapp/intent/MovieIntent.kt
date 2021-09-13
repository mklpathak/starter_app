package com.samplemovieapp.intent

sealed class MovieIntent {
    object FetchMovies : MovieIntent()
}