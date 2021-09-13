package com.core.ui


import androidx.recyclerview.widget.DiffUtil
import com.core.models.BaseModel

class ItemDiffCallback<T:BaseModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem===newItem
    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}