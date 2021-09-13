package com.core.models

import com.core.ModelTypes
import com.core.utils.Status

abstract class BaseModel (){
   abstract fun  getViewType(): Int
   abstract fun  getItemSpan(): Int
   override fun equals(other: Any?): Boolean {
      return super.equals(other)
   }
}

