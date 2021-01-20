package com.example.movie_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.BaseModel
import com.example.core.models.MovieResponse
import com.example.core.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class PostDetailViewModel   constructor(
    private val postDetailRepository: PostDetailRepository
): ViewModel(){

    private val _res = MutableLiveData<Resource<MovieResponse>>()
    val res : LiveData<Resource<MovieResponse>>
        get() = _res

     fun refreshData(id:Int)  = viewModelScope.launch {
        supervisorScope {
            try {
                var popularMoviesDeffered =   async { postDetailRepository.getMovieById(464052) }
                popularMoviesDeffered.await().body()?.backdrop_path?.let {
                    Log.e("mukul",it)
                }
            }catch (e:Exception){
                //  _res.postValue(Resource.error("failed",data))
            }
        }

    }


}