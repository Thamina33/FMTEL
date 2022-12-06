package com.example.fmtel.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentShopProfileBinding
import com.example.fmtel.model.CategoryListResponse
import com.example.fmtel.model.ShopProfileResponse
import com.example.fmtel.model.ProductListResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopProfileFragment : Fragment() {
    private lateinit var binding: FragmentShopProfileBinding
    var profileData : ShopProfileResponse.Data? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfile()
        binding.edit.setOnClickListener {

            if(profileData != null){
                val  bundle = Bundle()
                bundle.putSerializable("profileData" , profileData)
                findNavController().navigate(R.id.editShopInformationFragment, bundle)
            }


        }
    }

    private fun loadProfile() {
        (activity as MainActivity).showLoader()
        val  profileCall  = ApiProvider.dataApi.shopInformation()
        profileCall.enqueue(object :Callback <ShopProfileResponse?> {
            override fun onResponse(
                call: Call<ShopProfileResponse?>,
                response: Response<ShopProfileResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        profileData = resp.data

                        Log.d("TAG", "onResponse: ${resp.message}")

                            binding.shopName.text= resp.data.brand_name
                            binding.ownerName.text=resp.data.owner_name
                            binding.regionName.text= resp.data.region
                            binding.city.text=resp.data.city
                            binding.district.text=resp.data.district
                            binding.address.text=resp.data.address
                            binding.regDate.text = resp.data.registration_date
                            binding.repId.text = resp.data.user_id.toString()




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


            override fun onFailure(call: Call<ShopProfileResponse?>, t: Throwable) {

            }

        })


    }

    override fun onResume() {
        super.onResume()
        loadProfile()
    }
}