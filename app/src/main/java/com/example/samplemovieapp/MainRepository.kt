package com.example.samplemovieapp

import android.util.Log
import com.example.samplemovieapp.Constants.NOW_PLAYING
import com.example.samplemovieapp.Constants.POPULAER_MOVIES
import com.example.samplemovieapp.Constants.TOP_RATED
import com.example.samplemovieapp.Constants.UPCOMMING_MOVIES
import com.example.samplemovieapp.database.PopularDao
import com.example.samplemovieapp.models.Popular
import com.example.samplemovieapp.network.ApiHelper
import com.example.samplemovieapp.utils.Resource
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val popularDao: PopularDao
){
//    suspend fun getPouplarMovies() = apiHelper.getPopularMovies()
//    suspend fun getUpcommingMovies() =apiHelper.getUpcomming()
//    suspend fun getNowPlaying() =apiHelper.getNowPlaying()
//    suspend fun getTopRated() =apiHelper.getTopRated()

    fun getCacheSupport() = popularDao.getAll()

    suspend fun getUpcommingMovies(): Resource<Popular> {
       var  response=  apiHelper.getUpcomming()
        response.body()?.results?.let {
            it.forEach { insertOrUpdate(it, UPCOMMING_MOVIES) }
        }

        return Resource.error("No data", null)
    }
    suspend fun getPouplarMovies(): Resource<Popular> {
        var  response=  apiHelper.getPopularMovies()
        response.body()?.results?.let {
            it.forEach { insertOrUpdate(it, POPULAER_MOVIES) }
            return Resource.success(response.body())
        }
        return Resource.error("No data", null)
    }
    suspend fun getNowPlaying(): Resource<Popular> {
        var  response=  apiHelper.getNowPlaying()
        response.body()?.results?.let {
            it.forEach { insertOrUpdate(it, NOW_PLAYING) }
            return Resource.success(response.body())
        }
        return Resource.error("No data", null)
    }
    suspend fun getTopRated(): Resource<Popular> {
        var  response=  apiHelper.getTopRated()
        response.body()?.results?.let {
            it.forEach { insertOrUpdate(it, TOP_RATED) }
            return Resource.success(response.body())
        }
        return Resource.error("No data", null)
    }

    suspend fun insertOrUpdate(it: Popular.Result,tag: Int) {
        try{
            var list =  popularDao.getItemById(it.id)
            if (list.isEmpty()){
                it.categories =  "$tag"
                popularDao.insert(it)
            }
            else{
                var movie = list[0]
                if (!movie.categories.contains(tag.toString())){
                    movie.categories += ",$tag"
                    popularDao.updateCategories(it.id,movie.categories)

                }
            }
        } catch (e:Exception){
            Log.e("mukul",e.message.toString())

        }
    }

}

