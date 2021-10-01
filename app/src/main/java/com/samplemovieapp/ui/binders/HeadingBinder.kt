package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import com.core.ModelTypes
import com.core.models.Header
import com.core.ui.BaseModel
import com.core.ui.DataBindViewHolder
import com.core.ui.DataViewHolder
import com.samplemovieapp.databinding.ItemHeaderBinding

class HeadingBinder : DataViewHolder<BaseModel> {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder<BaseModel> {
        return ViewHolder(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
    class ViewHolder(var itemHeaderBinding:  ItemHeaderBinding) : DataBindViewHolder<BaseModel>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            itemHeaderBinding.title.text  = (multiViewItem as Header).title
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.HEADER
    }
}