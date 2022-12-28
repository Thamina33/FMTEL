package com.example.fmtel.fragments.operation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.MainActivity
import com.example.fmtel.R
import com.example.fmtel.adapter.SaleTransactionHisAdapter
import com.example.fmtel.databinding.FragmentSaleTransactionHistoryBinding
import com.example.fmtel.model.dailySattlementResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaleTransactionHistoryFragment : Fragment() , SaleTransactionHisAdapter.Interaction {
    private lateinit var binding: FragmentSaleTransactionHistoryBinding
    private lateinit var mAdapter: SaleTransactionHisAdapter
    var reportList: MutableList<dailySattlementResponse.Data> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaleTransactionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printMe = PrintMe(binding.root.context)
        mAdapter = SaleTransactionHisAdapter(this)
        reportList = ArrayList()
        binding.reportRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        loadReport()

    }

    private fun loadReport() {
        (activity as MainActivity).showLoader()
        val reportCall = ApiProvider.dataApi.getDailySattlementReport()



        reportCall.enqueue(object : Callback<dailySattlementResponse?> {
            override fun onResponse(
                call: Call<dailySattlementResponse?>,
                response: Response<dailySattlementResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")
                        reportList.clear();
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
                        "Server Error" ,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }


            override fun onFailure(call: Call<dailySattlementResponse?>, t: Throwable) {

            }

        })


    }

    override fun onItemSelected(position: Int, item: dailySattlementResponse.Data) {



    }
}