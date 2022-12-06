package com.example.fmtel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.SharedPrefManager
import com.example.fmtel.Utils.Helper
import com.example.fmtel.Utils.lastLoginDate
import com.example.fmtel.Utils.userKey
import com.example.fmtel.databinding.FragmentPaymentBinding
import com.example.fmtel.model.*
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaymentFragment : Fragment() {
   private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBAlance()
        val  itemModel = arguments?.getSerializable("model") as ProductListResponse.Data.ProductItem?



        if(itemModel != null){
            //binding.packageName.text = itemModel.name
            binding.packageId.text = itemModel.name
            binding.price.text = itemModel.price


            binding.pricee.text = itemModel.quantity.toString()

            Glide.with(requireContext())
                .load(itemModel?.image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.logo)

            val counter = binding.price.text.toString().toFloat()
            val itemPriceInInt = itemModel.quantity.toFloat()
            val sstotalPrice = counter * itemPriceInInt

            binding.totalPrice.text = sstotalPrice.toString()

            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()
        binding.printBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Processing..." , Toast.LENGTH_LONG).show()
            sasles_add(itemModel?.id.toString(), itemModel?.price.toString(), itemModel?.quantity.toString())
        }

    }

    private fun sasles_add(productid: String, price: String, quantity: String) {
        (activity as MainActivity).showLoader()
        val salesCall = ApiProvider.dataApi.salesAdd(
            product_id= productid , price =  price , quantity =  quantity
        )



        salesCall.enqueue(object : Callback<salesAdd?> {
            override fun onResponse(
                call: Call<salesAdd?>,
                response: Response<salesAdd?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

//                    if (resp != null) {
//
//                        SharedPrefManager.put(resp.data , userKey)
//                        SharedPrefManager.put(Helper.getCurrentDate() , lastLoginDate)
//
//                        goToHome()
//
//                    }


                } else if (response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Server Error",
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


            override fun onFailure(call: Call<salesAdd?>, t: Throwable) {

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


            override fun onFailure(call: Call<BalanceResponse?>, t: Throwable) {

            }

        })


    }

}