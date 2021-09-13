package com.core.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.core.models.BaseModel

fun<T:BaseModel> RecyclerView.registerViewTypes(vararg models: DataHolderModels,
                                   getController: (controller:DataBindListAdaptor<T>) -> Unit){
    val baseViewHolderProvider = DataController<T>()
    models.forEach {
        baseViewHolderProvider.registerBinder(it)
    }
    var dataBindListAdaptor = DataBindListAdaptor(baseViewHolderProvider, ItemDiffCallback())
    this.adapter = dataBindListAdaptor
    getController(dataBindListAdaptor)
    this.layoutManager = LinearLayoutManager(this.context)
}

fun <A:ViewBinding,T:BaseModel> DataController<T>.registerBinder(getModelType:()->Int, getView: (parent:View) -> A,setData:(A ,BaseModel)->Unit){
    this.apply {
       var model =ModelHelper({
           parent -> ViewHolderHelper(getView(parent),setData)
       },getModelType())
        registerBinder(model)
    }
}