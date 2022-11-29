package com.example.fmtel.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.R
import com.example.fmtel.model.BrandListResponse

class BrandAdapter (
    private val brandList: ArrayList<BrandListResponse.BrandItem>,
    private  val NavController: NavController,
    private val context: Context
    ):RecyclerView.Adapter<BrandAdapter.BrandViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BrandAdapter.BrandViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_brand,
                parent, false
            )

            return BrandViewHolder(itemView)
        }
    override fun onBindViewHolder(holder: BrandAdapter.BrandViewHolder, position: Int) {

        holder.brandName.text = brandList[position].name
       // holder.brandImg.setImageDrawable( brandList.get(position).image)
        Glide.with(holder.itemView.context)
            .load(brandList[position].image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.brandImg);

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id" , brandList[position].id)
            bundle.putSerializable("model" , brandList[position])
            NavController.navigate(R.id.packageByBrandFragment , bundle)
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return brandList.size
    }
    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val brandName: TextView = itemView.findViewById(R.id.brand_name)
        val brandImg: ImageView = itemView.findViewById(R.id.brand_img)



    }
}