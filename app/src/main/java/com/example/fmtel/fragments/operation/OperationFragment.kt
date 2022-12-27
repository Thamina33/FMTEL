package com.example.fmtel.fragments.operation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
import com.example.fmtel.SharedPrefManager
import com.example.fmtel.SigninActivity
import com.example.fmtel.databinding.FragmentOperationBinding

class OperationFragment : Fragment() {
   private lateinit var binding: FragmentOperationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentOperationBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.payentReport.setOnClickListener {
            findNavController().navigate(R.id.paymentReportragment)
        }
        binding.dailySattlementReport.setOnClickListener {
            findNavController().navigate(R.id.dailysattlementFragment)
        }
        binding.saleTransactionHis.setOnClickListener {
            findNavController().navigate(R.id.saleTransactionHistoryFragment)
        }
        binding.logOut.setOnClickListener {

            SharedPrefManager.nukeAllData()
            requireActivity().run{
                startActivity(Intent(this, SigninActivity::class.java).apply {
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })

                finish()
            }
        }



       // binding.txt2.text = binding.txt1.text.chunked(4).joinToString(separator  = " ")

        }

}