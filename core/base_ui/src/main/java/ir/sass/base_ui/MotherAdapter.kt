package ir.sass.base_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * this is the base class for all adapters
 * @param wrapper is for setting of the adapter, it holds layout and an alternate function for onBindViewHolder
 * @param DB is generic type for dataBinding
 * @param DataType is generic type for data type
 *
 * @property changeList will replace the current list with a new list
 */

class MotherAdapter<DB : ViewDataBinding , DataType> (
    private val wrapper: RecyclerItemWrapper<DB,DataType>
) : RecyclerView.Adapter<MotherAdapter.MotherAdapterVH<DB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotherAdapterVH<DB> =
        MotherAdapterVH(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), wrapper.layout, parent, false)
        )

    private var lastPos = -1
    override fun onBindViewHolder(
        holder: MotherAdapterVH<DB>,
        position: Int
    ) {
        if(position > lastPos){
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                duration = 500
            }
            holder.binding.root.startAnimation(anim)
        }
        lastPos = lastPos.coerceAtLeast(position)

        wrapper.bindingFun.invoke(holder.binding,wrapper.list[position],position)
        if(position == wrapper.list.size - 1)
            wrapper.ended.invoke()
    }

    override fun getItemCount(): Int = wrapper.list.size


    fun changeList(list: List<DataType>){
        val diffCallback = DiffCallback(wrapper.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        wrapper.list.clear()
        wrapper.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged() // I have to call this , if I won't I get crash
    }

    fun addToList(list: List<DataType>){
        val newList = mutableListOf<DataType>()
        newList.addAll(wrapper.list)
        newList.addAll(list)
        val diffCallback = DiffCallback(wrapper.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        wrapper.list.clear()
        wrapper.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged() // I have to call this , if I won't I get crash
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    class DiffCallback<DataType>(private val old : List<DataType>, private val new : List<DataType>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] === new[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] == new[newItemPosition]
    }

    class MotherAdapterVH<DB : ViewDataBinding>(var binding : DB) : RecyclerView.ViewHolder(binding.root)

}

/**
 * this is a wrapper class for holding layout and an alternate function for onBindViewHolder
 * @param layout is layout reference
 * @param bindingFun is an alternate function for onBindViewHolder
 * @param ended is a callback and it will invoked when you scroll to the end
 */

data class RecyclerItemWrapper<DB : ViewDataBinding, DataType>(
    @LayoutRes
    var layout : Int,
    val ended : () -> Unit = {},
    val bindingFun : (binding : DB,item : DataType,pos : Int) -> Unit

){
    internal val list = mutableListOf<DataType>()
}


