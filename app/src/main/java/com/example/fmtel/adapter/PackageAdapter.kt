package com.example.fmtel.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.R
import com.example.fmtel.model.BrandListResponse
import com.example.fmtel.model.PackageListResponse

class PackageAdapter (

    private val packageList: ArrayList<PackageListResponse.Data.PackageItem>,
    private  val NavController: NavController,
    private val context: Context
    ): RecyclerView.Adapter<PackageAdapter.PackageViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):PackageAdapter.PackageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_category,
            parent, false
        )

        return PackageAdapter.PackageViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: PackageAdapter.PackageViewHolder, position: Int) {

        holder.PackageName.text = packageList[position].name


        // holder.brandImg.setImageDrawable( brandList.get(position).image)


        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id" , packageList[position].id)
            bundle.putSerializable("model" , packageList[position])
            NavController.navigate(R.id.productByPackageFragment , bundle)
        }
    }
    override fun getItemCount(): Int {
        // on below line we are
        // returning our size of our list
        return packageList.size
    }
    class PackageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val PackageName: TextView = itemView.findViewById(R.id.category_name)




    }

}