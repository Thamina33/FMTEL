package com.example.fmtel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fmtel.databinding.FragmentMainHostBinding
import com.example.fmtel.model.BalanceResponse
import com.example.fmtel.model.ContianerPagerAdapter
import com.example.fmtel.networking.ApiProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainHostFragment : Fragment() {

    private lateinit var adapter: ContianerPagerAdapter
//   private lateinit var binding: FragmentHomeBinding
//   private lateinit var binding: FragmentOperationBinding
    private lateinit var binding: FragmentMainHostBinding

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            //   Fragment selected Fragment = null ;
            when (menuItem.itemId) {

                R.id.home -> binding.fragContainer.setCurrentItem(0, false)
                R.id.operation -> binding.fragContainer.setCurrentItem(1, false)
                R.id.setting -> binding.fragContainer.setCurrentItem(2, false)


            }
            true
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentbind
        binding = FragmentMainHostBinding.inflate(inflater , container , false )
        adapter = ContianerPagerAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadBAlance()
        binding.fragContainer.adapter = adapter
        binding.fragContainer.isUserInputEnabled = false

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    private fun loadBAlance() {
        val  balanceCall  = ApiProvider.dataApi.getBalance()
        balanceCall.enqueue(object : Callback<BalanceResponse?> {
            override fun onResponse(
                call: Call<BalanceResponse?>,
                response: Response<BalanceResponse?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Log.d("TAG", "onResponse: ${resp.message}")
                        binding.toolbar.availableBalance.text = resp.data.available




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