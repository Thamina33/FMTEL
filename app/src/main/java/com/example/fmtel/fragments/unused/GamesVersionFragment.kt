package com.example.fmtel.fragments.unused

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentGamesVersionBinding

class GamesVersionFragment : Fragment() {
   private lateinit var binding: FragmentGamesVersionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentGamesVersionBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.packageName.setOnClickListener {
            findNavController().navigate(R.id.paymentMethodPackageFragment)
        }
    }


}