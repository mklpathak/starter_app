package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.Header
import com.core.ui.DataBindViewHolder
import com.core.ui.DataHolderModels
import com.samplemovieapp.R
import com.samplemovieapp.databinding.ItemHeaderBinding

class HeadingBinder : DataHolderModels {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder {
        return ViewHolder(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
    class ViewHolder(var itemHeaderBinding:  ItemHeaderBinding) : DataBindViewHolder(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            super.onBindVewHolder(position, multiViewItem)
            val item = multiViewItem as Header
            itemHeaderBinding.title.text  = item.title
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.HEADER
    }
}