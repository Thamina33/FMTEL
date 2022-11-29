package com.example.fmtel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.R
import com.example.fmtel.adapter.ProductAdapter
import com.example.fmtel.databinding.FragmentProductByPackageBinding
import com.example.fmtel.model.BrandListResponse
import com.example.fmtel.model.PackageListResponse
import com.example.fmtel.model.ProductListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductByPackageFragment : Fragment() , ProductAdapter.Interaction {

    private lateinit var binding: FragmentProductByPackageBinding
    private  lateinit var  mAdapter: ProductAdapter
     var productList: MutableList<ProductListResponse.Data.ProductItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProductByPackageBinding.inflate(inflater, container, false)
        mAdapter = ProductAdapter(this)

        //passing model
        val model = arguments?.getSerializable("model") as PackageListResponse.Data.PackageItem?
        val itemmodel = arguments?.getSerializable("modeli") as BrandListResponse.BrandItem?
        productList = ArrayList()
        binding.productRV.apply {
            layoutManager = GridLayoutManager(requireContext() , 2 )
            adapter = mAdapter
        }

//
//        Glide.with(requireContext())
//
////
//            .load(brandList[position].image)
////            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
////            .into(holder.brandImg);

        if(model != null){
            binding.packageName.text = model.name
            loadProduct(model.id)
            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()


        return binding.root
        }

    override fun onItemSelected(position: Int, item: ProductListResponse.Data.ProductItem
    , type : String ) {

        if(type == "print"){
            if(item.quantity == 0){
                Toast.makeText(requireContext() , "Please Add Quantity to select this" , Toast.LENGTH_LONG).show()
            }
            else{
                val bundle = Bundle()
                bundle.putSerializable("model" , item)
                findNavController().navigate(R.id.paymentFragment , bundle)
            }

        }else if(type == "minus"){

            if(item.quantity > 0 ){
                val newQuantity = item.quantity -1

                productList[position].quantity = newQuantity
                mAdapter.notifyItemChanged(position)
            }

        }else if (type == "add"){
            val newQuantity = item.quantity + 1
            productList[position].quantity = newQuantity
            mAdapter.notifyItemChanged(position)
        }


    }

    private fun loadProduct(id: Int) {
        val  BrandCall  = ApiProvider.dataApi.getProductByPackage(packageID = id.toString())



        BrandCall.enqueue(object : Callback<ProductListResponse?> {
            override fun onResponse(
                call: Call<ProductListResponse?>,
                response: Response<ProductListResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")
                        productList.clear() ;
                        mAdapter.setBgIage(resp.data.`package`.brand_background_image)
                        productList.addAll(resp.data.products)
                         mAdapter.submitList(productList)


                    }


                } else if (response.isSuccessful && response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Token Invalid",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Server Error" + { response.code() },
                        Toast.LENGTH_LONG
                    ).show()
                }

            }


            override fun onFailure(call: Call<ProductListResponse?>, t: Throwable) {

            }

        })


    }
}