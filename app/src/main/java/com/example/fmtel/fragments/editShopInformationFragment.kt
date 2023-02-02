package com.example.fmtel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fmtel.MainActivity
import com.example.fmtel.databinding.FragmentEditShopInformationBinding
import com.example.fmtel.model.ShopProfileResponse
import com.example.fmtel.model.UpdateShopInfo
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class editShopInformationFragment : Fragment() {

    private lateinit var binding: FragmentEditShopInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditShopInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val updateModel = arguments?.getSerializable("profileData") as ShopProfileResponse.Data?

        binding.cancleBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        if (updateModel != null) {

//            val brand_name = binding.shopName.text
//            val owner_name = binding.ownerName.text
//            val region = binding.regionName.text
//            val city = binding.city.text
//            val district = binding.district.text
//            val address = binding.address.text
//            val rep_id = binding.repId.text
//            val reg = binding.regDate.text
            binding.shopName.setText(updateModel.brand_name)
            binding.ownerName.setText(updateModel.owner_name)
            binding.regionName.setText(updateModel.region)
            binding.city.setText(updateModel.city)
            binding.district.setText(updateModel.district)
            binding.address.setText(updateModel.address)
            binding.repId.setText(updateModel.user_id.toString())
            binding.regDate.setText(updateModel.registration_date)



//            val counter = binding.price.text.toString().toFloat()
//            val itemPriceInInt = updateModel.quantity.toFloat()
//            val sstotalPrice = counter * itemPriceInInt
//
//            binding.totalPrice.text = sstotalPrice.toString()

            //  Toast.makeText(requireContext() , model.name , Toast.LENGTH_LONG).show()
        } else Toast.makeText(requireContext(), "Null data not found", Toast.LENGTH_SHORT).show()
        binding.UpdateBtn.setOnClickListener {

            val brand_name = binding.shopName.text.toString()
            val owner_name = binding.ownerName.text.toString()
            val region = binding.regionName.text.toString()
            val city = binding.city.text.toString()

            val district = binding.district.text.toString()
            val address = binding.address.text.toString()

            val user_id = binding.repId.text.toString()
            val registration_date = binding.regDate.text.toString()

            updateInfo(
                updateModel?.id.toString(),
               brand_name.toString(),
              owner_name.toString(),
                region.toString(),
              city.toString(),
               district.toString(),
                address.toString(),
               user_id.toString(),
               registration_date.toString(),

                )


        }


}
    private fun updateInfo(id: String, brand_name: String, owner_name: String,
                           region: String, city: String,district: String,
                           address:String, user_id: String, registration_date: String,) {
        (activity as MainActivity).showLoader()
        val  updateCall = ApiProvider.dataApi.postShopInfo(id = id,
           brand_name = brand_name , owner_name = owner_name , region = region , city = city,
            district = district, address = address, user_id = user_id , registration_date = registration_date
        )



        updateCall.enqueue(object : Callback<UpdateShopInfo?> {

            override fun onResponse(
                call: Call<UpdateShopInfo?>,
                response: Response<UpdateShopInfo?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Toast.makeText(
                            requireContext(),
                            "Profile Updated",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    findNavController().popBackStack()

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
                        "Server Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


            override fun onFailure(call: Call<UpdateShopInfo?>, t: Throwable) {

            }

        })


    }
}