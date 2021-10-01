package com.samplemovieapp

import android.util.Log
import com.core.Constants.NOW_PLAYING
import com.core.Constants.POPULAER_MOVIES
import com.core.Constants.TOP_RATED
import com.core.Constants.UPCOMMING_MOVIES
import com.core.database.PopularDao
import com.core.models.Popular
import com.core.network.ApiHelper
import com.core.utils.Resource
import com.core.utils.Result
import com.core.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val popularDao: PopularDao
){

    fun getUpcommingMovies():Flow<Result<List<Popular.Result>>>{
        return networkBoundResource(
            query = {popularDao.fetchByCategory("$UPCOMMING_MOVIES")},
            onFetchFailed = {

            },
            saveFetchResult = {
                with(it){
                    body()?.let {
                        if (isSuccessful){
                            it.results?.let {
                                it.map { it.categories = "$UPCOMMING_MOVIES" }
                            }
                            popularDao.insertAll(it.results)
                        }
                    }
                }
            },
            fetch = {
                apiHelper.getUpcomming()
            },
            shouldFetch = {
                it?.isEmpty() ?: true
            }
        ).flowOn(Dispatchers.IO)
    }

    fun getPouplarMovies():Flow<Result<List<Popular.Result>>>{
        return networkBoundResource(
            query = {popularDao.fetchByCategory("$POPULAER_MOVIES")},
            onFetchFailed = {

            },
            saveFetchResult = {
                with(it){
                    body()?.let {
                        if (isSuccessful){
                            it.results?.let {
                                it.map { it.categories = "$POPULAER_MOVIES" }
                            }
                            popularDao.insertAll(it.results)
                        }
                    }
                }
            },
            fetch = {
                apiHelper.getPopularMovies()
            },
            shouldFetch = {
                it?.isEmpty() ?: true
            }
        ).flowOn(Dispatchers.IO)
    }

    fun getNowPlaying():Flow<Result<List<Popular.Result>>>{
        return networkBoundResource(
            query = {popularDao.fetchByCategory("$NOW_PLAYING")},
            onFetchFailed = {
            },
            saveFetchResult = {
                with(it){
                    body()?.let {
                        if (isSuccessful){
                            it.results?.let {
                                it.map { it.categories = "$NOW_PLAYING" }
                            }
                            popularDao.insertAll(it.results)
                        }
                    }
                }
            },
            fetch = {
                apiHelper.getNowPlaying()
            },
            shouldFetch = {
                it?.isEmpty() ?: true
            }
        ).flowOn(Dispatchers.IO)
    }

    fun getTopRated():Flow<Result<List<Popular.Result>>>{
        return networkBoundResource(
            query = {popularDao.fetchByCategory("$TOP_RATED")},
            onFetchFailed = {

            },
            saveFetchResult = {
                with(it){
                    body()?.let {
                        if (isSuccessful){
                            it.results?.let {
                                it.map { it.categories = "$TOP_RATED" }
                            }
                            popularDao.insertAll(it.results)
                        }
                    }
                }
            },
            fetch = {
                apiHelper.getTopRated()
            },
            shouldFetch = {
                it?.isEmpty() ?: true
            }
        ).flowOn(Dispatchers.IO)
    }


}

