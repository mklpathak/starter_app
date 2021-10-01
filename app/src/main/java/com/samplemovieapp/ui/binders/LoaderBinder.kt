package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import com.core.ModelTypes
import com.core.models.LoadingModel
import com.core.ui.BaseModel
import com.core.ui.DataBindViewHolder
import com.core.ui.DataViewHolder
import com.samplemovieapp.databinding.ItemLoaderBinding


class LoaderBinder : DataViewHolder<BaseModel> {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder<BaseModel> {


        return ViewHolder(ItemLoaderBinding.inflate(LayoutInflater.from(parent.context)))
    }
    class ViewHolder(var itemHeaderBinding: ItemLoaderBinding) : DataBindViewHolder<BaseModel>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            val item = multiViewItem as LoadingModel
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.LOADING
    }
}