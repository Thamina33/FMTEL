package com.example.fmtel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


            binding.quantity.text = item.quantity.toString() + " Cards"
            binding.date.text = item.date
            binding.price.text = item.price
            binding.packageName.text = item.product_name
            binding.transcastionCode.text = item.transaction_id

            binding.printBtn.setOnClickListener {
//                binding.printView.price.text = item.price.toString()
//                binding.printView.date.text = item.date.toString()
//                binding.printView.expiryDate.text = item.expiry_date.toString()
//                binding.printView.trasactionNo.text = item.transaction_id.toString()
//                binding.printView.serialNo.text=item.serial_no
//                binding.printView.time.text = item.time
//                binding.printView.tid.text = item.user_id
                val printMe = PrintMe(binding.root.context)

              if(item.quantity == item.codes.size){
                  for(i in 1..item.quantity.toInt()){

                      Log.d("QUATITY", "bind:${item.quantity}   $i")
                      binding.printView.BrandName.text = item.brand_name
                      binding.printView.rechargeMsg.text = item.recharge_message
                      binding.printView.price.text = item.product_name
                      binding.printView.tid.text = item.user_id.toString()
                      binding.printView.date.text = item.date.toString()
                      binding.printView.expiryDate.text =
                          item?.codes?.get(i-1)?.expiry_date.toString()
                      binding.printView.trasactionNo.text = item.transaction_id.toString()
                      binding.printView.serialNo.text =item?.codes?.get(i-1)?.serial_number.toString()
                      binding.printView.pinCode.text = item?.codes?.get(i-1)?.code.toString().chunked(4).joinToString(" ")

                      printMe.sendViewToPrinter(binding.printView.printImg)

                      // withContext(Dispatchers.Main) {

//                      if(item.quantity > 1){
//                          Thread.sleep(1500)
//                      }

                      // }
                      // delay(500)






                  }
              }else {
                  Toast.makeText(context , "proper Codes not received" , Toast.LENGTH_LONG).show()
              }
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: dailySattlementResponse.Data)
    }
}
