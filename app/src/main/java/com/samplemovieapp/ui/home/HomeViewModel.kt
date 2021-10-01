package com.samplemovieapp.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.core.Constants
import com.core.ModelTypes
import com.core.models.*
import com.core.ui.BaseModel
import com.samplemovieapp.MainRepository
import com.core.utils.*
import com.samplemovieapp.intent.MovieIntent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.*


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

class HomeViewModel  constructor(
    private val mainRepository: MainRepository
):ViewModel(){

    companion object {
        val POPULAR_MOVIES = "Popular Movies" to 1
        val UPCOMMING_MOVIES = "Upcomming Movies" to 3
        val NOW_PLAYING = "Now Playing" to 5
        val TOP_RATED_MOVIES = "Top Rated Movies" to 7
    }

    val userIntent = Channel<MovieIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState>
        get() = _state

    val mutableLiveData = MutableLiveData<HomeState> ()
    val liveData : LiveData<HomeState>
            get() = mutableLiveData

    private val data = ArrayList<BaseModel>().apply {
        add(Header(POPULAR_MOVIES.first))
        add(LoadingModel())
        add(Header(UPCOMMING_MOVIES.first))
        add(LoadingModel())
        add(Header(NOW_PLAYING.first))
        add(LoadingModel())
        add(Header(TOP_RATED_MOVIES.first))
        add(LoadingModel())
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MovieIntent.FetchMovies -> {
                        refreshData()
                    }
                }
            }
        }
    }
    private fun refreshData()  = viewModelScope.launch (Dispatchers.Main){

        instantCombineWithTag(
            NOW_PLAYING to mainRepository.getNowPlaying(),
            TOP_RATED_MOVIES to mainRepository.getTopRated(),
            POPULAR_MOVIES to mainRepository.getPouplarMovies(),
            UPCOMMING_MOVIES to mainRepository.getUpcommingMovies()
        ).collect {
            data[it.first.second] = it.second.convertToBaseModel {
                BaseModelWrapper(it,itemType = ModelTypes.MOVIES)
            }
            mutableLiveData.value = HomeState.Movies(data = data)
        }
    }
}