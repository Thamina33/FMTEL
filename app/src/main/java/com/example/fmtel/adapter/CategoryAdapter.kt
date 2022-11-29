package com.example.fmtel.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fmtel.R
import com.example.fmtel.fragments.TelecommunicationOperatorFragment
import com.example.fmtel.model.CategoryListResponse

class CategoryAdapter(
    private val categoryList: ArrayList<CategoryListResponse.CategoryItem>,
    private val context: Context,
    private  val NavController: NavController
):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CategoryAdapter.CategoryViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_category,
                parent, false
            )

            return CategoryViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {

            holder.categoryName.text = categoryList.get(position).name
            holder.itemView.setOnClickListener{
                val bundle = Bundle()
                 bundle.putInt("id" , categoryList[position].id)
                bundle.putSerializable("model" , categoryList[position])
               NavController.navigate(R.id.telecommunicationOperatorFragment , bundle)
            }

        }
        override fun getItemCount(): Int {

            return categoryList.size
        }
        class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val categoryName: TextView = itemView.findViewById(R.id.category_name)

        }
    }