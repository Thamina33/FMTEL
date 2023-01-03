package com.example.fmtel.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.MainActivity
import com.example.fmtel.databinding.FragmentPaymentBinding
import com.example.fmtel.model.*
import com.example.fmtel.networking.ApiProvider
import kotlinx.coroutines.*
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
        val  brandModel = arguments?.getSerializable("brandmodel") as Brand?

        val printMe = PrintMe(binding.root.context)

        if (brandModel != null){

            binding.packageName.text = brandModel.name
            //binding.invoicePage.BrandName.text = brandModel.name
            Glide.with(requireContext())
            .load(brandModel.background_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
           .into(binding.backImg);
        }


        if(itemModel != null){
            //binding.packageName.text = itemModel.name
            binding.packageId.text = itemModel.name
            binding.price.text = itemModel.price
            binding.pricee.text = itemModel.qty.toString()

            Glide.with(requireContext())
                .load(itemModel?.image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.logo)

            val counter = binding.price.text.toString().toFloat()
            val itemPriceInInt = itemModel.qty.toFloat()
            val sstotalPrice = counter * itemPriceInInt
            binding.totalPrice.text = sstotalPrice.toString()
//            binding.invoicePage.price.text = sstotalPrice.toString()



            //tid , transaction id, date, serial no, expiry date



            binding.printBtn.setOnClickListener {

                val itemPriceInInt = itemModel.qty.toFloat()
                val sstotalPrice = counter * itemPriceInInt
                if (sstotalPrice >= 150){
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Alert")
                    builder.setMessage("Total price is avobe 150 SAR. Are you sure want to continue")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->


                        sasles_add(itemModel?.id.toString(),
                            itemModel?.price.toString(),
                            itemModel?.qty.toString(),
                            printMe)


                    }

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->

                        dialog.dismiss()
                    }

                    builder.show()
                }

                else {


                    sasles_add(itemModel?.id.toString(),
                        itemModel?.price.toString(),
                        itemModel?.qty.toString(),
                        printMe)
                }



            }

            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun sasles_add(productid: String,
                           price: String,
                           quantity: String,
                           printMe: PrintMe) {
        binding.pbar.visibility = View.VISIBLE
        val salesCall = ApiProvider.dataApi.salesAdd(
            product_id= productid ,
            price =  price ,
            quantity =  quantity
            )



        salesCall.enqueue(object : Callback<salesAdd?> {
            override fun onResponse(
                call: Call<salesAdd?>,
                response: Response<salesAdd?>
            ) {
                binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()
                    // findNavController().popBackStack()



                    for(i in 1..quantity.toInt()){

                            binding.invoicePage.BrandName.text = resp?.data?.brand_name.toString()
                           binding.invoicePage.time.text = resp?.data?.time.toString()
                           binding.invoicePage.price.text = resp?.data?.product_name.toString()
                           binding.invoicePage.rechargeMsg.text= resp?.data?.recharge_message.toString()
                            binding.invoicePage.tid.text = resp?.data?.user_id.toString()
                            binding.invoicePage.date.text = resp?.data?.date.toString()
                            binding.invoicePage.expiryDate.text =
                               resp?.data?.codes?.get(i-1)?.expiry_date.toString()
                            binding.invoicePage.trasactionNo.text = resp?.data?.transaction_id.toString()
                            binding.invoicePage.serialNo.text =resp?.data?.codes?.get(i-1)?.serial_number.toString()
                            binding.invoicePage.pinCode.text = resp?.data?.codes?.get(i-1)?.code.toString().chunked(4).joinToString(" ")

                            printMe.sendViewToPrinter(binding.invoicePage.printImg)

                           // withContext(Dispatchers.Main) {

                        if(quantity.toInt() > 1){
                            Thread.sleep(1500)
                        }

                           // }
                           // delay(500)






                    }
                    findNavController().popBackStack()





                   
  //                  printMe.sendViewToPrinter(binding.invoicePage.printImg)

//                    if (resp != null) {
//
//                        SharedPrefManager.put(resp.data , userKey)
//                        SharedPrefManager.put(Helper.getCurrentDate() , lastLoginDate)
//
//                        goToHome()
//
//                    }


                }

                else if (response.code() == 422) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "This product is already sold!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        "Server Error",
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
                        "Server Error",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }


            override fun onFailure(call: Call<BalanceResponse?>, t: Throwable) {

            }

        })


    }

    override fun onResume() {
        super.onResume()
        loadBAlance()
    }

}