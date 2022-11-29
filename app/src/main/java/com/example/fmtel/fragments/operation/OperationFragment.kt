package com.example.fmtel.fragments.operation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
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
            findNavController().navigate(R.id.dailySattlementReportFragment)
        }
        binding.saleTransactionHis.setOnClickListener {
            findNavController().navigate(R.id.saleTransactionHistoryFragment)
        }
    }
}