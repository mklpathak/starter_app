package com.samplemovieapp.ui.binders

import android.view.View
import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.ErrorModel
import com.core.models.Header
import com.core.models.LoadingModel
import com.core.ui.DataBindViewHolder
import com.core.ui.DataHolderModels
import com.samplemovieapp.databinding.ItemErrorBinding
import com.samplemovieapp.databinding.ItemHeaderBinding

class ErrorBinder() : DataHolderModels {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder<ErrorModel> {
        return ViewHolder(ItemErrorBinding.bind(parent))
    }
    class ViewHolder(var itemHeaderBinding: ItemErrorBinding) : DataBindViewHolder<ErrorModel>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: ErrorModel) {
            super.onBindVewHolder(position, multiViewItem)
            val item = multiViewItem as ErrorModel
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.ERROR
    }
}