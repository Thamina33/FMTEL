package com.example.fmtel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.R
import com.example.fmtel.databinding.ItemSaleTransactionHistoryBinding
import com.example.fmtel.model.dailySattlementResponse

class SaleTransactionHisAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<dailySattlementResponse.Data>() {

        override fun areItemsTheSame(
            oldItem: dailySattlementResponse.Data,
            newItem: dailySattlementResponse.Data
        ): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(
            oldItem: dailySattlementResponse.Data,
            newItem: dailySattlementResponse.Data
        ): Boolean {
            return oldItem == newItem
        }


    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sale_transaction_history,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<dailySattlementResponse.Data>) {
        differ.submitList(list)
    }

    class ViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: dailySattlementResponse.Data) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val binding = ItemSaleTransactionHistoryBinding.bind(itemView)

            binding.productCode.text = item.product.code
            binding.date.text = item.date
            binding.price.text = item.price
            binding.packageName.text = item.product.name
            binding.transcastionCode.text = item.product.code

            binding.printBtn.setOnClickListener {
                binding.printView.price.text = item.price.toString()
                binding.printView.date.text = item.date.toString()
                val printMe = PrintMe(binding.root.context)
                printMe.sendViewToPrinter(binding.printView.printImg)
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: dailySattlementResponse.Data)
    }
}
