package com.core.utils

import androidx.recyclerview.widget.GridLayoutManager
import com.core.Constants
import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.ErrorModel

data class Resource<out T : BaseModel>( val status: Status, val data: T? = null, val message:String?=null , val itemType:Int = -1 ,val throwable: ErrorModel?=null , var spanSize:Int = 4):BaseModel(){
    companion object{
        fun <T : BaseModel> success(data:T?): Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T:BaseModel> error(errorModel: ErrorModel): Resource<T>{
            return Resource(Status.ERROR, throwable = errorModel,itemType = errorModel.getViewType())
        }
        fun <T:BaseModel> loading(): Resource<T>{
            return Resource(Status.LOADING,itemType = ModelTypes.LOADING)
        }

    }
    override fun getViewType(): Int  = data?.getViewType()?:itemType

    override fun getItemSpan()= data?.getItemSpan()?:spanSize

}