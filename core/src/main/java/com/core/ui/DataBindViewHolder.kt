package com.core.ui


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.core.models.BaseModel

abstract class DataBindViewHolder<T:BaseModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun onBindVewHolder(position:Int, multiViewItem: T) {

    }
    open fun onViewDetached(position: Int, multiViewItem: T) {
    }

}