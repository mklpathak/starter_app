package com.core.models

import com.core.Constants


data class Header (var title: String): BaseModel() {
    override fun getViewType(): Int {
        return Constants.HEADER;
    }
}