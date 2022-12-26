package com.example.fmtel.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.fmtel.R
import com.example.fmtel.databinding.ActivityMainBinding.bind
import com.example.fmtel.databinding.ItemDailySattlementBinding
import com.example.fmtel.databinding.ItemProductBinding
import com.example.fmtel.model.dailySattlementResponse
import com.example.fmtel.model.DailySalesResponse.Data

class DailySattlementAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
             return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DailySattlement(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_daily_sattlement,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailySattlement -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Data>) {
        differ.submitList(list)
    }

    class DailySattlement
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Data) {
            val  binding = ItemDailySattlementBinding.bind(itemView)
            binding.packageName.text = item.`package`
            binding.brandName.text = item.product
            binding.price.text = item.price
            binding.quantity.text = item.quantity.toString()
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Data)
    }
}
