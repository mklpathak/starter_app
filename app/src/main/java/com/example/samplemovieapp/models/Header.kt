package com.example.samplemovieapp.models

import com.example.samplemovieapp.Constants

data class Header (var title: String): BaseModel() {
    override fun getViewType(): Int {
        return Constants.HEADER;
    }
}