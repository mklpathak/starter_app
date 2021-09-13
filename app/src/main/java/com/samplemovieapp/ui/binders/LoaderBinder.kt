package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.ErrorModel
import com.core.models.LoadingModel
import com.core.ui.DataBindViewHolder
import com.core.ui.DataHolderModels
import com.core.ui.ModelHelper
import com.core.ui.ViewHolderHelper
import com.samplemovieapp.databinding.ItemErrorBinding
import com.samplemovieapp.databinding.ItemLoaderBinding
import com.samplemovieapp.databinding.ItemLoaderBindingImpl


class LoaderBinder : DataHolderModels {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder {


        return ViewHolder(ItemLoaderBinding.inflate(LayoutInflater.from(parent.context)))
    }
    class ViewHolder(var itemHeaderBinding: ItemLoaderBinding) : DataBindViewHolder(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            super.onBindVewHolder(position, multiViewItem)
            val item = multiViewItem as LoadingModel

        }

    }

    override fun getViewType(): Int {
        return ModelTypes.LOADING
    }
}