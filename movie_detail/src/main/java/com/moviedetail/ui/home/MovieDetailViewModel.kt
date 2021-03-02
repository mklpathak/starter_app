package com.moviedetail.ui.home

import android.util.Log
import android.util.StateSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviedetail.MovieDetailRepository
import com.core.models.MovieResponse
import com.core.utils.Resource
import com.core.utils.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MovieDetailViewModel   constructor(
    private val postDetailRepository: MovieDetailRepository
): ViewModel(){

    private val _res = MutableLiveData<Resource<MovieResponse>>()
    val res : LiveData<Resource<MovieResponse>>
        get() = _res

     fun refreshData(id:Int)  = viewModelScope.launch {
        supervisorScope {
            try {
                var popularMoviesDeffered =   async { postDetailRepository.getMovieById(id) }
                popularMoviesDeffered.await().body()?.let {
                    _res.postValue(Resource.success(it))
                    return@supervisorScope
                }

                _res.postValue(Resource.error("Data loading failed",null))
                return@supervisorScope

            }catch (e:Exception){
                //  _res.postValue(Resource.error("failed",data))
                _res.postValue(Resource.error("Data loading failed",null))

            }
        }

    }


}