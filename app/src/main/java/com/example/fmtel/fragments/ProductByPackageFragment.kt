package com.example.fmtel.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.adapter.ProductAdapter
import com.example.fmtel.databinding.FragmentProductByPackageBinding
import com.example.fmtel.model.BalanceResponse
import com.example.fmtel.model.BrandListResponse
import com.example.fmtel.model.PackageListResponse
import com.example.fmtel.model.ProductListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductByPackageFragment : Fragment() , ProductAdapter.Interaction {
    var avl_bal = 0.0
    private  var repsose : ProductListResponse? = null
    private lateinit var binding: FragmentProductByPackageBinding
    private  lateinit var  mAdapter: ProductAdapter
     var productList: MutableList<ProductListResponse.Data.ProductItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProductByPackageBinding.inflate(inflater, container, false)

        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBAlance()
        (binding.productRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter = ProductAdapter(this , this)

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
            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_SHORT).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_SHORT).show()


    }



    private fun loadProduct(id: Int) {
        binding.pbar.visibility = View.VISIBLE
        val  BrandCall  = ApiProvider.dataApi.getProductByPackage(packageID = id.toString())



        BrandCall.enqueue(object : Callback<ProductListResponse?> {
            override fun onResponse(
                call: Call<ProductListResponse?>,
                response: Response<ProductListResponse?>
            ) {
                binding.pbar.visibility = View.GONE
               // (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {
                        repsose = resp
                        Log.d("TAG", "onResponse: ${resp.message}")
                        productList.clear() ;
                        mAdapter.setBgIage(resp.data.`package`.brand_background_image)


                        for(itm in resp.data.products){
                            itm.qty = 1
                        }
                        productList.addAll(resp.data.products)
                      //  Log.d("TAG", "onResponse: ${resp.data.products.first()}")
                         mAdapter.submitList(productList)
                        //Log.d("TAG", "onResponse: ${mAdapter.itemCount} ")


                    }


                } else if (response.isSuccessful && response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Token Invalid",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Server Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


            override fun onFailure(call: Call<ProductListResponse?>, t: Throwable) {

            }

        })


    }

    private fun loadBAlance() {
        val  balanceCall  = ApiProvider.dataApi.getBalance()
        balanceCall.enqueue(object :Callback <BalanceResponse?> {
            override fun onResponse(
                call: Call<BalanceResponse?>,
                response: Response<BalanceResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")

                        binding.availableBalance.text= resp.data.available
                        try {
                            avl_bal = resp.data.available.toDouble()
                        }catch (e : Exception){}

                    }


                } else if (response.isSuccessful && response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Token Invalid",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Server Error" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


            override fun onFailure(call: Call<BalanceResponse?>, t: Throwable) {

            }

        })


    }

   fun getBalance() : Double{
       return avl_bal
   }

    override fun onItemSelected(position: Int, item: ProductListResponse.Data.ProductItem
                                , type : String ) {

        if(type == "print"){

            if(item.stock == 0 ){
                Toast.makeText(requireContext() , "Stock Out" , Toast.LENGTH_SHORT).show()

                return
            }

            if(item.qty == 0 ){
                Toast.makeText(requireContext() , "Please Add Quantity to select this" , Toast.LENGTH_SHORT).show()
            }
            else{
                val bundle = Bundle()
                bundle.putSerializable("model" , item)
                bundle.putSerializable("brandmodel" , repsose?.data?.brand)

                findNavController().navigate(R.id.paymentFragment , bundle)
            }

        }
        else if(type == "minus")
        {

            if(item.qty > 0 ){
                val newQuantity = item.qty -1

                productList[position].qty = newQuantity
                mAdapter.notifyItemChanged(position)
            }

        }else if (type == "add"){
            val newQuantity = item.qty + 1
            productList[position].qty = newQuantity
            mAdapter.notifyItemChanged(position)
        }


    }

    override fun onResume() {
        super.onResume()
        loadBAlance()
    }
}