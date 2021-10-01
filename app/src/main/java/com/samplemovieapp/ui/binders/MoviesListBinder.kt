package com.samplemovieapp.ui.binders

import android.view.LayoutInflater
import android.view.View
import com.core.ModelTypes
import com.core.models.BaseModelWrapper
import com.core.models.Popular
import com.core.ui.DataBindViewHolder
import com.core.ui.AdaptiveList
import com.core.ui.BaseModel
import com.core.ui.DataViewHolder
import com.samplemovieapp.databinding.ItemHorizontalListBinding
import com.samplemovieapp.databinding.ItemPopularMovieBinding

class MoviesListBinder : DataViewHolder<BaseModel> {
    override fun createInstance(parent: View, viewType: Int): DataBindViewHolder <BaseModel>{
        return ViewHolder(ItemHorizontalListBinding.inflate(LayoutInflater.from(parent.context)))
    }
    class ViewHolder(var itemHeaderBinding: ItemHorizontalListBinding) : DataBindViewHolder<BaseModel>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: BaseModel) {

            multiViewItem as BaseModelWrapper<List<Popular.Result>>

            AdaptiveList<BaseModel>().apply {
                registerViewHolders(getKey = {
                    ModelTypes.MOVIES
                }, getView = {
                    ItemPopularMovieBinding.inflate(LayoutInflater.from(it.context))
                }, setData = { binding, data ->
                    val item = data as Popular.Result
                    binding.movie = item
                })
                setUpRecyclerView(itemHeaderBinding.recyclerView,1,orientation =  AdaptiveList.VIEW_TYPE_HORIZONTAL)
                submitList(multiViewItem.data)
            }
        }
    }

    override fun getViewType(): Int {
        return ModelTypes.MOVIES
    }
}