package com.samplemovieapp.ui.home

import com.core.ui.BaseModel


sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Movies(val data: List<BaseModel>) : HomeState()
    data class Error(val error: String?) : HomeState()
}