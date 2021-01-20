package com.example.core.network

import com.example.core.models.Popular
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getPopularMovies() =  apiService.getPopularMovies(1)
    override suspend fun getNowPlaying() = apiService.getNowPlaying(1)
    override suspend fun getUpcomming()= apiService.getUpcoming(1)
    override suspend fun getTopRated() = apiService.getTopRated(1)
    override suspend fun getMovieById(id: Int) = apiService.getMovieById(id)
}
