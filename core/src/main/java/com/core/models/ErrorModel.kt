package com.core.models

import com.core.ModelTypes

class ErrorModel constructor(throwable: Throwable? = null,var spanSize : Int =4): BaseModel() {

    override fun getViewType(): Int {
        return ModelTypes.ERROR
    }

    override fun getItemSpan() = spanSize
}