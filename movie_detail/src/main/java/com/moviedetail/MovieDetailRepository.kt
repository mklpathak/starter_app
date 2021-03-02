package com.moviedetail

import com.core.database.PopularDao
import com.core.network.ApiHelper
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val popularDao: PopularDao
){

   suspend fun getMovieById(id:Int) = apiHelper.getMovieById(id)
}