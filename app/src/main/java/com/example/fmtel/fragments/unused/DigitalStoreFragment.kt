package com.example.fmtel.fragments.unused

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentDigitalStoreBinding

class DigitalStoreFragment : Fragment() {
  private lateinit var binding: FragmentDigitalStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentDigitalStoreBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appStore.setOnClickListener {
            findNavController().navigate(R.id.paymentMethodFragment)
        }
    }

}