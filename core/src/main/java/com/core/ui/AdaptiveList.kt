package com.core.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.viewbinding.ViewBinding



/**
 * BaseModel -> All the models / data objects should be of instance
 * BaseModel and need to override getViewType and getItemSpan
 * getViewType -> Should always be unique as getViewType will be used later on by recyclerview
 * getItemSpan as how much space will the UI will take
 */
abstract class BaseModel (){
    abstract fun  getViewType(): Int
    abstract fun  getItemSpan(): Int
}


/**
 * Adaptive List -> Scalable recyclerview solution
 * Provide diffUtil to handle better comparison between data in listadaptor
 */


class AdaptiveList<T:BaseModel>(diffUtil: DiffUtil.ItemCallback<T>? = null) {
    /**
     datamaps holds DataHolder which extends view holder
     with key as modeltypes
     */
    private val dataMaps = HashMap<Int, DataViewHolder<T>>()

    /**
     * dataBindListAdaptor works as internal adaptor
     */
    private val dataBindListAdaptor  = DataBindListAdaptor(this, (diffUtil
        ?: ItemDiffCallback())
    )

    private var recyclerView:RecyclerView?= null

    /**
     * createViewHolder will be invoked by dataBindListAdaptor
     * to create view instance from available viewholders in datamaps using viewtype
     * throws RuntimeException when there is no Models are available in datamaps
     */

    fun createViewHolder(parent: ViewGroup, viewType: Int) =
        dataMaps[viewType]?.createInstance(parent, viewType)
            ?: throw RuntimeException("Register Models before use")

    /**
     * registerView -> store models in datamap
     */


    fun registerViewHolders(viewViewHolder: DataViewHolder<T>) {
        dataMaps[viewViewHolder.getViewType()] = viewViewHolder
    }


//    fun getDataAdaptor () = dataBindListAdaptor

    /**
     * submitList provide data to list adaptor
     */

    fun submitList(list : List<T>) {
        dataBindListAdaptor.submitList(list)
    }

    /**
     * registerViewHolders -> helps to create a viewholder using unit functions
     * getKey -> Should be unique
     * getItem should return A viewBinding : returns a view ->
     * then view gets wrapped in viewholder using modelhelper
     * setData will be used to update data where A is the View and T is the data you will be get which is of BaseModel
     */

    fun <A: ViewBinding > registerViewHolders(getKey:()->Int, getView: (parent:View) -> A, setData:(A, T)->Unit){
        this.apply {
            val model =ModelHelper({
                    parent -> ViewHolderHelper(getView(parent),setData)
            },getKey())
            registerViewHolders(model)
        }
    }

    /**
     * spanInterface how much space will a particular model will take
     * helps to create girdview
     */
    private var spanInterface = object: SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when {
                dataBindListAdaptor.getSpan(position)!=-1 -> dataBindListAdaptor.getSpan(position)
                else -> 4
            }
        }
    }


    /**
     * setUpRecyclerView helps to attach adaptive list to recyclerview
     * rowscount useful to create a grid
     * orientation to mark it as Veritcle list or Horizontal List
     */

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


    /**
     * Internal adaptor
     */

    private class DataBindListAdaptor<T:BaseModel>(
        private val viewHolderProvider: AdaptiveList<T>,
        diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, DataBindViewHolder<T>>(
        diffUtil
    ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindViewHolder<T> {
            return viewHolderProvider.createViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: DataBindViewHolder<T>, position: Int) {
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


    private class ViewHolderHelper<T:ViewBinding,C:BaseModel>(var itemHeaderBinding: T, var  setData: (T , C) -> Unit,
    ) : DataBindViewHolder<C>(itemHeaderBinding.root) {
        override fun onBindVewHolder(position: Int, multiViewItem: C) {
            setData(itemHeaderBinding,multiViewItem)
        }
    }


   private class ModelHelper<B:ViewBinding,A:ViewHolderHelper<B,C>,C:BaseModel>(var holder:(parent:View)->A, var itemType: Int) : DataViewHolder<C> {
        override fun createInstance(parent: View, viewType: Int): DataBindViewHolder<C> {
            return holder(parent)
        }
        override fun getViewType(): Int {
            return itemType
        }
    }
}

/**
 * This DiffUtil will be used if there is no diffutil is priovided
 */


class ItemDiffCallback<T:BaseModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem===newItem
    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}





/**
 * All the custom binders should be of type DataViewHolder
 * registerViewHolders will only support viewbinding
 * please create a custom binder incase if you dont want to use view binding
 */


interface DataViewHolder<T:BaseModel> {
    fun createInstance(parent: View, viewType : Int) : DataBindViewHolder<T>
    fun getViewType():Int
}


/**
 * All the custom viewholders should be of type DataBindViewHolder
 */
abstract class DataBindViewHolder<T:BaseModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindVewHolder(position:Int, multiViewItem: T)
}



