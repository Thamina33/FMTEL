package com.example.fmtel.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.R
import com.example.fmtel.databinding.ItemProductBinding
import com.example.fmtel.fragments.ProductByPackageFragment
import com.example.fmtel.model.ProductListResponse.Data.ProductItem

var num =0
 var totalPrice = 0
class ProductAdapter(private val interaction: Interaction? = null ,
                     private val fragment: ProductByPackageFragment? = null) :


    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var bgImage = ""
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductItem>() {

        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            ),
            interaction,
            getBgIage(),
            fragment
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is viewholder -> {
                holder.bind(differ.currentList[position])

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ProductItem>) {
        differ.submitList(list)
    }

    fun getBgIage(): String {
        return bgImage
    }

    fun setBgIage(link : String) {
        bgImage = link
    }


    class viewholder(
        itemView: View,
        private val interaction: Interaction?,
        private val bgIage: String,
        private val fragment: ProductByPackageFragment?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ProductItem) {
            val binding = ItemProductBinding.bind(itemView)

            binding.packageName.text = item.name
            binding.price.text = item.price
            var productQty = item.qty
            binding.counterTxt.text = item.qty.toString()
           // item.quantity = 1

            val prc = item.price.toFloat()
            val qnt = item.qty.toInt()
            val total_prc = prc*qnt

            binding.printBtn.setOnClickListener {

                if(fragment != null){
                    if(fragment.getBalance() > total_prc){
                        interaction?.onItemSelected(adapterPosition, item , "print")
                    }else {
                        Toast.makeText(itemView.context , "Your Balance is low" , Toast.LENGTH_LONG).show()
                    }
                }else {
                    Toast.makeText(itemView.context , "Something went wrong." , Toast.LENGTH_LONG).show()

                }
            }


            binding.increaseBtn.setOnClickListener {

                interaction?.onItemSelected(adapterPosition, item , "add")

            }
            binding.dicreaseBtn.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item , "minus")

            }

            Glide.with(itemView.context)
                .load(bgIage)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.backImg)
//            binding.printBtn.setOnClickListener {
//                val counter = binding.counterTxt.text.toString().toInt()
//                val itemPriceInInt = item.price.toInt()
//                totalPrice = counter * itemPriceInInt
//                 }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ProductItem, type : String)
    }
}




