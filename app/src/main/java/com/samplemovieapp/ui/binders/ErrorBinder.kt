package com.samplemovieapp.ui.binders

import android.view.View
import com.core.ModelTypes
import com.core.models.ErrorModel
import com.core.ui.BaseModel
import com.core.ui.DataBindViewHolder
import com.core.ui.DataViewHolder
import com.samplemovieapp.databinding.ItemErrorBinding

class ErrorBinder() : DataViewHolder<BaseModel> {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder<BaseModel> {
        return ViewHolder(ItemErrorBinding.bind(parent))
    }
    class ViewHolder(var itemHeaderBinding: ItemErrorBinding) : DataBindViewHolder<BaseModel>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            val item = multiViewItem as ErrorModel
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.ERROR
    }
}