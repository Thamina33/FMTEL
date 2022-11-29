package com.example.fmtel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
        binding.shopProfile.setOnClickListener {
            findNavController().navigate(R.id.shopProfileFragment)
        }
        binding.terminalBalance.setOnClickListener {
            findNavController().navigate(R.id.terminalBalanceFragment)
        }
        binding.pinCode.setOnClickListener {
            findNavController().navigate(R.id.changePinCodeFragment)
        }
    }

}