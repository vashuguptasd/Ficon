package com.example.ficon.asking_coarse_fragments.adapter_and_dataClass

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ficon.databinding.ChoooseCoarseSingleListItemBinding

class CoarseFragmentRecyclerViewAdapter(
    private val headingSize : Int ,
    private val bodySize : Int ,
    private val clickListener: ClickListener
) : ListAdapter<CoarseDataClass, CoarseFragmentRecyclerViewAdapter.CoarseViewHolder>(CoarseDiffUtil()) {


    class CoarseDiffUtil : DiffUtil.ItemCallback<CoarseDataClass>() {
        override fun areItemsTheSame(oldItem: CoarseDataClass, newItem: CoarseDataClass): Boolean {
            return oldItem.coarse == newItem.coarse


        }

        override fun areContentsTheSame(
            oldItem: CoarseDataClass,
            newItem: CoarseDataClass
        ): Boolean {
            return oldItem==newItem
        }

    }

    class CoarseViewHolder(private val binding : ChoooseCoarseSingleListItemBinding) : RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun create(parent: ViewGroup): CoarseViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChoooseCoarseSingleListItemBinding.inflate(layoutInflater)
                return CoarseViewHolder(binding)
            }
        }

        fun bind(
            item: CoarseDataClass?,
            clickListener: ClickListener,
            headingSize: Int,
            bodySize: Int,
        ) {


            binding.coarseXmlVariable = item
            binding.xmlClickVariable = clickListener
            binding.headingTextView.textSize = headingSize.toFloat()
            binding.bodyTextView.textSize = bodySize.toFloat()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoarseViewHolder {
        return CoarseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CoarseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener,headingSize,bodySize)
    }

}
class ClickListener (val click : (id : String )->Unit){
    fun onClick(id:String) = click(id)
}