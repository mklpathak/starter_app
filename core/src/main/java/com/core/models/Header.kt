package com.core.models

import com.core.Constants
import com.core.ModelTypes
import com.core.ui.BaseModel
import com.core.utils.Status


data class Header (var title: String ): BaseModel() {
    override fun getViewType(): Int {
        return ModelTypes.HEADER;
    }
    override fun getItemSpan() = 4
}