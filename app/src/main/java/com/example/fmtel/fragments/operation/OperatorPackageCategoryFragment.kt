package com.example.fmtel.fragments.operation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentOperatorPackageCategoryBinding

class OperatorPackageCategoryFragment : Fragment() {

     private lateinit var binding: FragmentOperatorPackageCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOperatorPackageCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.package11.setOnClickListener {
            findNavController().navigate(R.id.purshaseOperatorPackageFragment)
        }
    }

}