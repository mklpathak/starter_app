package com.core.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.core.models.BaseModel


class DataBindListAdaptor<T:BaseModel>(
    private val viewHolderProvider: DataController<T>,
    diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, DataBindViewHolder>(
    diffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindViewHolder {
        return viewHolderProvider.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: DataBindViewHolder, position: Int) {
        holder.onBindVewHolder(position, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getViewType()
    }

    fun getSpan(position: Int): Int {
        return try{
            getItem(position).getItemSpan()
        } catch (e:IndexOutOfBoundsException){
            -1
        }
    }
}