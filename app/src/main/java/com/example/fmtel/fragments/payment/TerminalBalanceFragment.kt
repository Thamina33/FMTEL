package com.example.fmtel.fragments.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentTerminalBalanceBinding
import com.example.fmtel.model.BalanceResponse
import com.example.fmtel.model.ShopProfileResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class TerminalBalanceFragment : Fragment() {
private lateinit var binding: FragmentTerminalBalanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTerminalBalanceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printMe = PrintMe(binding.root.context)
        loadBAlance()

        val sdf = SimpleDateFormat("dd.MM.yyyyy 'Time:' HH:mm:ss")
        val currentDateandTime = sdf.format(Date())
        binding.printView.date.text = currentDateandTime

        binding.printBtn.setOnClickListener {
            printMe.sendViewToPrinter(binding.printView.printImg)
        }
        binding.okBtn.setOnClickListener {
            findNavController().popBackStack()
        }
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
                        val printMe = PrintMe(binding.root.context)
                        binding.availableBalance.text= resp.data.available
                        binding.currentBalance.text= resp.data.current
                        binding.receivedBalance.text= resp.data.reserved

                        binding.printView.availableBalance.text = resp.data.available
                        binding.printView.currentBalance.text= resp.data.current
                        binding.printView.receivedBalance.text= resp.data.reserved


                    }


                } else if (response.isSuccessful && response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Token Invalid",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Server Error",
                        Toast.LENGTH_SHORT
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