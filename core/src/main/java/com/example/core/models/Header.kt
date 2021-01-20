package com.example.core.models

import com.example.core.Constants


data class Header (var title: String): BaseModel() {
    override fun getViewType(): Int {
        return Constants.HEADER;
    }
}