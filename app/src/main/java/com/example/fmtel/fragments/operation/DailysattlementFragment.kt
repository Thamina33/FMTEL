package com.example.fmtel.fragments.operation

import android.content.Context
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
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.adapter.DailySattlementAdapter
import com.example.fmtel.adapter.PackageAdapter
import com.example.fmtel.adapter.ProductAdapter
import com.example.fmtel.databinding.FragmentDailysattlementBinding
import com.example.fmtel.model.*
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DailysattlementFragment : Fragment(),DailySattlementAdapter.Interaction {

    private lateinit var binding: FragmentDailysattlementBinding
    private  lateinit var  mAdapter: DailySattlementAdapter
    var reportList: MutableList<DailySalesResponse.Data> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDailysattlementBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printMe = PrintMe(binding.root.context)
        mAdapter = DailySattlementAdapter(this)

      //  val model = arguments?.getSerializable("model") as DailySalesResponse.Data?
        reportList = ArrayList()

        binding.reportRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

            loadReport()
            loadProfile()
        binding.printBtn.setOnClickListener {

            val sdf = SimpleDateFormat("'Date:' dd.MM.yyyyy  'Time:' HH:mm:ss")
            val currentDateandTime = sdf.format(Date())

            binding.printView.currentDate.text = currentDateandTime

            printMe.sendViewToPrinter(binding.printView.printImg)
            printMe.sendViewToPrinter(binding.reportRV)
            printMe.sendTextToPrinter("\nPowered By FM TEL\nDeveloped By SPINNER TECH\n\n" , 24f, true , false , 1)

            findNavController().popBackStack()
        }

    }

    private fun loadReport() {
        (activity as MainActivity).showLoader()
        val  reportCall  = ApiProvider.dataApi.dailySales()



        reportCall.enqueue(object : Callback<DailySalesResponse?> {
            override fun onResponse(
                call: Call<DailySalesResponse?>,
                response: Response<DailySalesResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")
                        reportList.clear() ;
                       // mAdapter.setBgIage(resp.data.`package`.brand_background_image)
                        reportList.addAll(resp.data)
                        mAdapter.submitList(reportList)


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


            override fun onFailure(call: Call<DailySalesResponse?>, t: Throwable) {

            }

        })


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



                        Log.d("TAG", "onResponse: ${resp.message}")

                        binding.printView.name.text= resp.data.brand_name
                        binding.printView.userName.text=resp.data.owner_name
                        binding.printView.userId.text = resp.data.user_id.toString()




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

    override fun onItemSelected(position: Int, item: DailySalesResponse.Data) {

    }
}