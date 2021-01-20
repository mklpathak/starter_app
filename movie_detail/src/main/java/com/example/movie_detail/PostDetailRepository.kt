package com.example.movie_detail

import com.example.core.database.PopularDao
import com.example.core.network.ApiHelper
import javax.inject.Inject

class PostDetailRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val popularDao: PopularDao
){

   suspend fun getMovieById(id:Int) = apiHelper.getMovieById(id)
}