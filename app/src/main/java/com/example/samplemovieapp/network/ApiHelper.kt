package com.example.samplemovieapp.network

import com.example.samplemovieapp.models.Popular
import retrofit2.Response

interface ApiHelper {
    suspend fun getPopularMovies(): Response<Popular>
    suspend fun getNowPlaying(): Response<Popular>
    suspend fun getUpcomming(): Response<Popular>
    suspend fun getTopRated(): Response<Popular>
}