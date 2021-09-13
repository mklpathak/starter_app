package com.core.ui


import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.viewbinding.ViewBinding
import com.core.models.BaseModel


class DataController<T:BaseModel>(var diffUtil: DiffUtil.ItemCallback<T>? = null) {
    private val dataMaps = HashMap<Int, DataHolderModels>()
    private val dataBindListAdaptor  = DataBindListAdaptor(this, (diffUtil
        ?: ItemDiffCallback())
    )
    private var recyclerView:RecyclerView?= null

    fun createViewHolder(parent: ViewGroup, viewType: Int) =
        dataMaps[viewType]?.createInstance(parent, viewType)
            ?: throw RuntimeException("Register Models before use")


    fun registerBinder(viewHolderModels: DataHolderModels) {
        dataMaps[viewHolderModels.getViewType()] = viewHolderModels
    }
    fun getDataAdaptor () = dataBindListAdaptor

    fun <A: ViewBinding> registerBinder(getModelType:()->Int, getView: (parent:View) -> A, setData:(A, BaseModel)->Unit){
        this.apply {
            var model =ModelHelper({
                    parent -> ViewHolderHelper(getView(parent),setData)
            },getModelType())
            registerBinder(model)
        }
    }
    var spanInterface = object: SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when {
                dataBindListAdaptor.getSpan(position)!=-1 -> dataBindListAdaptor.getSpan(position)
                else -> 4
            }
        }
    }

    fun setUpRecyclerView(recyclerView: RecyclerView,rowsCount: Int = 1,orientation:Int =VIEW_TYPE_VERTICLE){
        this.recyclerView=recyclerView
        this.recyclerView?.apply {
            layoutManager = if (rowsCount==0||rowsCount==1){
                LinearLayoutManager(this.context).apply {
                    this.orientation = if (orientation== VIEW_TYPE_HORIZONTAL){
                        LinearLayoutManager.HORIZONTAL
                    }else{
                        LinearLayoutManager.VERTICAL
                    }
                }
            }else{
                GridLayoutManager(this.context,rowsCount).apply {
                    this.orientation = if (orientation== VIEW_TYPE_HORIZONTAL){
                        GridLayoutManager.HORIZONTAL
                    }else{
                        GridLayoutManager.VERTICAL
                    }
                    spanSizeLookup = spanInterface
                }
            }
            adapter = dataBindListAdaptor
        }
    }

    companion object {
        const val VIEW_TYPE_HORIZONTAL = 1000
        const val VIEW_TYPE_VERTICLE = 1001
    }
}
interface DataHolderModels {
    fun createInstance(parent: View, viewType : Int) : DataBindViewHolder
    fun getViewType():Int
}