package com.example.fmtel.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fmtel.MainActivity
import com.example.fmtel.adapter.CategoryAdapter
import com.example.fmtel.databinding.FragmentHomeBinding
import com.example.fmtel.model.BAnnerResponse
import com.example.fmtel.model.CategoryListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var categoryRV: RecyclerView
    lateinit var categoryRVAdapter: CategoryAdapter
    lateinit var categoryList: ArrayList<CategoryListResponse.CategoryItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding.telecommunication.setOnClickListener {
//            findNavController().navigate(R.id.telecommunicationOperatorFragment)
//        }
//        binding.digitalStore.setOnClickListener {
//            findNavController().navigate(R.id.digitalStoreFragment)
//        }
//        binding.games.setOnClickListener {
//            findNavController().navigate(R.id.gamesFragment)
//        }

        categoryList = ArrayList()
        val  layoutManager = LinearLayoutManager(context)
        binding.categoryRV.layoutManager = layoutManager
        categoryRVAdapter = CategoryAdapter(categoryList, requireContext() , findNavController())
        binding.categoryRV.adapter = categoryRVAdapter


        loadCategory()
        loadBanner()

    }

    private fun loadCategory() {
        (activity as MainActivity).showLoader()

        val  categoryCall  = ApiProvider.dataApi.getCategories()



        categoryCall.enqueue(object : Callback<CategoryListResponse?> {
            override fun onResponse(
                call: Call<CategoryListResponse?>,
                response: Response<CategoryListResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")

                        categoryList.addAll(resp.data)
                        categoryRVAdapter.notifyDataSetChanged()


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


            override fun onFailure(call: Call<CategoryListResponse?>, t: Throwable) {

            }

        })


    }
    private fun loadBanner() {
        val  BAnnerCall  = ApiProvider.dataApi.getBanner()
        BAnnerCall.enqueue(object :Callback <BAnnerResponse?> {
            override fun onResponse(
                call: Call<BAnnerResponse?>,
                response: Response<BAnnerResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")


                        Glide.with(requireContext())
                            .load(resp.data)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .into(binding.img);


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


            override fun onFailure(call: Call<BAnnerResponse?>, t: Throwable) {

            }

        })


    }

}
