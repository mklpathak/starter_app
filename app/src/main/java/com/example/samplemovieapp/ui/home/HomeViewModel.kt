package com.example.samplemovieapp.ui.home

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemovieapp.Constants
import com.example.samplemovieapp.MainRepository
import com.example.samplemovieapp.models.BaseModel
import com.example.samplemovieapp.models.Header
import com.example.samplemovieapp.models.Popular
import com.example.samplemovieapp.utils.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HomeViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
):ViewModel(){

    private val _res = MutableLiveData<Resource<List<BaseModel>>>()

    private val data = ArrayList<BaseModel>()

    val res : LiveData<Resource<List<BaseModel>>>
        get() = _res

    var dataLoaded = false


//    val cacheSupport : LiveData<List<Popular.Result>>
//        get() = mainRepository.getCacheSupport()

    init {
        setObserver()
        refreshData()
    }

    private fun setObserver()= viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepository.getCacheSupport().sample(1000).collect {
            data.clear()
            data.apply {
                add(Header("Popular Movies"))
                add(Popular(1, it.filter {
                    it.categories.contains(Constants.POPULAER_MOVIES.toString())
                }, 0, 0))
                add(Header("Upcomming Movies"))
                add(Popular(1, it.filter {
                    it.categories.contains(Constants.UPCOMMING_MOVIES.toString())
                }, 0, 0))
                add(Header("Now Playing"))
                add(Popular(1, it.filter {
                    it.categories.contains(Constants.NOW_PLAYING.toString())
                }, 0, 0))
                add(Header("Top rated Movies"))
                add(Popular(1, it.filter {
                    it.categories.contains(Constants.TOP_RATED.toString())
                }, 0, 0))
            }

            _res.postValue(Resource.success(data))

        }
    }
    private fun refreshData()  = viewModelScope.launch {
        supervisorScope {
            try {
                var popularMoviesDeffered =   async { mainRepository.getPouplarMovies() }
                var upcommingMoviesDeffered = async { mainRepository.getUpcommingMovies() }
                var nowPlayingDeffered =  async { mainRepository.getNowPlaying() }
                var topRatedDeffered = async { mainRepository.getTopRated() }
                popularMoviesDeffered.await()
                upcommingMoviesDeffered.await()
                nowPlayingDeffered.await()
                topRatedDeffered.await()


            }catch (e:Exception){
              //  _res.postValue(Resource.error("failed",data))
            }
        }

    }

}