package com.example.fmtel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSettingsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printMe = PrintMe(binding.root.context)
        binding.shopProfile.setOnClickListener {
            findNavController().navigate(R.id.shopProfileFragment)
        }
        binding.terminalBalance.setOnClickListener {
            findNavController().navigate(R.id.terminalBalanceFragment)
        }
        binding.pinCode.setOnClickListener {
            findNavController().navigate(R.id.changePinCodeFragment)
        }
        binding.testprinter.setOnClickListener {

            printMe.sendTextToPrinter("Test Printer" , 32f, true , false , 0)
//            Toast.makeText(requireContext() , "Processing...." , Toast.LENGTH_LONG).show()
        }
    }

}