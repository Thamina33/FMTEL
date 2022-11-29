package com.example.fmtel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fmtel.databinding.FragmentMainHostBinding
import com.example.fmtel.model.ContianerPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


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



        binding.fragContainer.adapter = adapter
        binding.fragContainer.isUserInputEnabled = false

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }
}