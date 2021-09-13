package com.core.ui

import android.view.View
import androidx.viewbinding.ViewBinding
import com.core.models.BaseModel
import com.core.models.LoadingModel

class ModelHelper<B:ViewBinding,A:ViewHolderHelper<B>>(var holder:(parent:View)->A, var itemType: Int) : DataHolderModels {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder {
        return holder(parent)
    }
    override fun getViewType(): Int {
        return itemType
    }
}


class ViewHolderHelper<T:ViewBinding>(var itemHeaderBinding: T,var  setData: (T , BaseModel) -> Unit = { a, b ->},
) : DataBindViewHolder(itemHeaderBinding.root) {
    override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
        super.onBindVewHolder(position, multiViewItem)
        setData(itemHeaderBinding,multiViewItem)
    }
}
