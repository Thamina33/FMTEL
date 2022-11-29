package com.example.fmtel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fmtel.R
import com.example.fmtel.SharedPrefManager
import com.example.fmtel.Utils.Helper
import com.example.fmtel.Utils.lastLoginDate
import com.example.fmtel.Utils.userKey
import com.example.fmtel.databinding.FragmentPaymentBinding
import com.example.fmtel.model.PackageListResponse
import com.example.fmtel.model.ProductListResponse
import com.example.fmtel.model.login_response
import com.example.fmtel.model.salesAdd
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
        val  itemModel = arguments?.getSerializable("model") as ProductListResponse.Data.ProductItem?
        val packagemodel = arguments?.getSerializable("modell") as PackageListResponse.Data.PackageItem?

        binding = FragmentPaymentBinding.inflate(inflater,container,false)

        if(itemModel != null){
            binding.packageName.text = packagemodel?.name
            binding.packageId.text = itemModel.name
            binding.price.text = itemModel.price
            binding.pricee.text = itemModel.price

            val counter = binding.price.text.toString().toFloat()
            val itemPriceInInt = itemModel.quantity.toFloat()
            val sstotalPrice = counter * itemPriceInInt

            binding.totalPrice.text = sstotalPrice.toString()

            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()
        binding.printBtn.setOnClickListener {
            sasles_add(itemModel?.id.toString(), itemModel?.price.toString(), itemModel?.quantity.toString())
        }

        return binding.root
    }

    private fun sasles_add(product_id: String, price: String, quantity: String) {
        val salesCall = ApiProvider.dataApi.salesAdd(
            product_id , price , quantity
        )



        salesCall.enqueue(object : Callback<salesAdd?> {
            override fun onResponse(
                call: Call<salesAdd?>,
                response: Response<salesAdd?>
            ) {
                // binding.pbar.visibility = View.GONE
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


                } else if (response.isSuccessful && response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Username and pass not matched",
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

}