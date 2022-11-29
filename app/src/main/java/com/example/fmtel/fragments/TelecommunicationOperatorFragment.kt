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
import androidx.recyclerview.widget.RecyclerView
import com.example.fmtel.adapter.BrandAdapter
import com.example.fmtel.databinding.FragmentTelecommunicationOperatorBinding
import com.example.fmtel.model.BrandListResponse
import com.example.fmtel.model.CategoryListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TelecommunicationOperatorFragment : Fragment() {

   private lateinit var binding: FragmentTelecommunicationOperatorBinding
    lateinit var brandRV: RecyclerView
    lateinit var brandRVAdapter: BrandAdapter
    lateinit var brandList: ArrayList<BrandListResponse.BrandItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTelecommunicationOperatorBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = arguments?.getSerializable("model") as CategoryListResponse.CategoryItem?


        brandList = ArrayList()
        val layoutManager  = GridLayoutManager(context,2)
        binding.brandRV.layoutManager = layoutManager
        brandRVAdapter = BrandAdapter(brandList,findNavController(), requireContext())
        binding.brandRV.adapter = brandRVAdapter

        if(model != null){
            binding.categoryName.text = model.name
            loadBrand(model.id)
            //Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        }else   Toast.makeText(requireContext() , "Null data not found" , Toast.LENGTH_LONG).show()




    }

    private fun loadBrand(id: Int) {
        val  BrandCall  = ApiProvider.dataApi.getBrands(category_id = id.toString())



        BrandCall.enqueue(object : Callback<BrandListResponse?> {
            override fun onResponse(
                call: Call<BrandListResponse?>,
                response: Response<BrandListResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")

                        brandList.addAll(resp.data.brands)
                        brandRVAdapter.notifyDataSetChanged()


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


            override fun onFailure(call: Call<BrandListResponse?>, t: Throwable) {

            }

        })


    }
}