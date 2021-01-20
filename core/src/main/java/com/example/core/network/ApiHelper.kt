package com.example.core.network

import com.example.core.models.MovieResponse
import com.example.core.models.Popular
import retrofit2.Response

interface ApiHelper {
    suspend fun getPopularMovies(): Response<Popular>
    suspend fun getNowPlaying(): Response<Popular>
    suspend fun getUpcomming(): Response<Popular>
    suspend fun getTopRated(): Response<Popular>
    suspend fun getMovieById(id:Int): Response<MovieResponse>

}