package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import com.core.ModelTypes
import com.core.models.BaseModel
import com.core.models.BaseModelWrapper
import com.core.models.Header
import com.core.models.Popular
import com.core.ui.DataBindViewHolder
import com.core.ui.DataController
import com.core.ui.DataHolderModels
import com.core.ui.registerBinder
import com.core.utils.Resource
import com.samplemovieapp.databinding.ItemHeaderBinding
import com.samplemovieapp.databinding.ItemHorizontalListBinding
import com.samplemovieapp.databinding.ItemPopularMovieBinding
import com.samplemovieapp.ui.home.HomeState

class MoviesListBinder : DataHolderModels {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder {
        return ViewHolder(ItemHorizontalListBinding.inflate(LayoutInflater.from(parent.context)))
    }
    class ViewHolder(var itemHeaderBinding: ItemHorizontalListBinding) : DataBindViewHolder(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {
            super.onBindVewHolder(position, multiViewItem)

            multiViewItem as BaseModelWrapper<List<Popular.Result>>
            DataController<BaseModel>().apply {
                registerBinder(getModelType = {
                    ModelTypes.MOVIES
                }, getView = {
                    ItemPopularMovieBinding.inflate(LayoutInflater.from(it.context))
                }, setData = { binding, data ->
                    val item = data as Popular.Result
                    binding.movie = item
                })
                setUpRecyclerView(itemHeaderBinding.recyclerView,1,orientation =  DataController.VIEW_TYPE_HORIZONTAL)
                getDataAdaptor().submitList(multiViewItem.data)
            }
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.MOVIES
    }
}