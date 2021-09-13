package com.core.models

import com.core.ModelTypes

data class LoadingModel (var isLoading:Boolean = true): BaseModel() {
    override fun getViewType(): Int {
        return ModelTypes.LOADING
    }

    override fun getItemSpan() = 4

}