package com.core.models

import com.core.ModelTypes

data class BaseModelWrapper<T>(var data:T,var itemType: Int = ModelTypes.NOT_SURE , var spanSize  : Int = 4) :BaseModel () {
    override fun getViewType()  =itemType
    override fun getItemSpan() = spanSize
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}