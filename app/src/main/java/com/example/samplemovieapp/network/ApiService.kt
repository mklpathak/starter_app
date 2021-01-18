package com.example.samplemovieapp.network

import com.example.samplemovieapp.models.Popular
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page:Int): Response<Popular>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page:Int): Response<Popular>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page:Int): Response<Popular>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page:Int): Response<Popular>
}