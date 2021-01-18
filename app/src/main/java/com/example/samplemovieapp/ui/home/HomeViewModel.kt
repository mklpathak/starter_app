package com.example.samplemovieapp.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemovieapp.MainRepository
import com.example.samplemovieapp.models.BaseModel
import com.example.samplemovieapp.models.Header
import com.example.samplemovieapp.models.Popular
import com.example.samplemovieapp.utils.Resource
import com.example.samplemovieapp.utils.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HomeViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
):ViewModel(){

    private val _res = MutableLiveData<Resource<List<BaseModel>>>()

    private val data = ArrayList<BaseModel>()

    val res : LiveData<Resource<List<BaseModel>>>
        get() = _res

    init {
        getEmployees()
    }
    private fun getEmployees()  = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        supervisorScope {

            try {
                var popularMoviesDeffered =   async { mainRepository.getPouplarMovies() }
                var upcommingMoviesDeffered = async { mainRepository.getUpcommingMovies() }
                var nowPlayingDeffered =  async { mainRepository.getNowPlaying() }
                var topRatedDeffered = async { mainRepository.getTopRated() }

                //   mainRepository.saveMoviesInDb(it.results)
                popularMoviesDeffered.await()?.let {
                    when(it.status){
                        Status.SUCCESS ->{
                            data.add(Header("Popular Movies"))
                            it.data?.let { it1 -> data.add(it1) }
                        }
                        else -> {

                        }
                    }
                }

                upcommingMoviesDeffered.await()?.let {
                    when(it.status){
                        Status.SUCCESS ->{
                            data.add(Header("Upcomming Movies"))
                            it.data?.let { it1 -> data.add(it1) }
                        }
                        else -> {

                        }
                    }
                }

                nowPlayingDeffered.await()?.let {
                    when(it.status){
                        Status.SUCCESS ->{
                            data.add(Header("Now Playing Movies"))
                            it.data?.let { it1 -> data.add(it1) }
                        }
                        else -> {

                        }
                    }
                }

                topRatedDeffered.await()?.let {
                    when(it.status){
                        Status.SUCCESS ->{
                            data.add(Header("Top rated Movies"))
                            it.data?.let { it1 -> data.add(it1) }
                        }
                        else -> {

                        }
                    }
                }
//                upcommingMoviesDeffered.await().body()?.let {
//                    data.add(Header("Upcomming Movies"))
//                    data.add(it)
//                }
//                nowPlayingDeffered.await().body()?.let {
//                    data.add(Header("Now Playing"))
//                    data.add(it)
//                }
//                topRatedDeffered.await().body()?.let {
//                    data.add(Header("Top rated"))
//                    data.add(it)
//                }
                _res.postValue(Resource.success(data))

            }catch (e:Exception){
                _res.postValue(Resource.error("failed",data))
            }
        }

    }

}