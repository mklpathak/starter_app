package com.core.network

import com.core.models.MovieResponse
import com.core.models.Popular
import retrofit2.Response

interface ApiHelper {
    suspend fun getPopularMovies(): Response<Popular>
    suspend fun getNowPlaying(): Response<Popular>
    suspend fun getUpcomming(): Response<Popular>
    suspend fun getTopRated(): Response<Popular>
    suspend fun getMovieById(id:Int): Response<MovieResponse>

}