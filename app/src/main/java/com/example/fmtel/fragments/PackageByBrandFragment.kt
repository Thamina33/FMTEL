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
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.adapter.BrandAdapter
import com.example.fmtel.adapter.PackageAdapter
import com.example.fmtel.databinding.FragmentPackageByBrandBinding
import com.example.fmtel.model.BalanceResponse
import com.example.fmtel.model.BrandListResponse
import com.example.fmtel.model.CategoryListResponse
import com.example.fmtel.model.PackageListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PackageByBrandFragment : Fragment() {
     private lateinit var binding: FragmentPackageByBrandBinding
    lateinit var packageRVAdapter: PackageAdapter
    lateinit var packageList: ArrayList<PackageListResponse.Data.PackageItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentPackageByBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBAlance()
        val model = arguments?.getSerializable("model") as BrandListResponse.BrandItem?



        packageList = ArrayList()
        val  layoutManager = LinearLayoutManager(context)
        binding.packageRV.layoutManager = layoutManager
        packageRVAdapter = PackageAdapter(packageList,findNavController(),requireContext())
        binding.packageRV.adapter = packageRVAdapter


//        brandList = ArrayList()
//        val layoutManager  = GridLayoutManager(context,2)
//        binding.brandRV.layoutManager = layoutManager
//        brandRVAdapter = BrandAdapter(brandList,findNavController(), requireContext())
//        binding.brandRV.adapter = brandRVAdapter

        if(model != null){
            binding.brandName.text = model.name

            loadPackage(model.id)
          //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()


    }
    private fun loadPackage(id: Int) {
        (activity as MainActivity).showLoader()
        val  BrandCall  = ApiProvider.dataApi.getPackageByBrand(brandID = id.toString())



        BrandCall.enqueue(object : Callback<PackageListResponse?> {
            override fun onResponse(
                call: Call<PackageListResponse?>,
                response: Response<PackageListResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")

                        packageList.addAll(resp.data.packages)
                        packageRVAdapter.notifyDataSetChanged()


                    }


                } else if (response.code() == 401) {
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


            override fun onFailure(call: Call<PackageListResponse?>, t: Throwable) {

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

    override fun onResume() {
        super.onResume()
        loadBAlance()
    }
}