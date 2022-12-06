package com.example.fmtel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.fmtel.databinding.ActivityMainBinding
import com.example.fmtel.databinding.FragmentHomeBinding
import com.example.fmtel.databinding.FragmentOperationBinding
import com.example.fmtel.databinding.FragmentSettingsBinding
import com.example.fmtel.model.ContianerPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var adapter: ContianerPagerAdapter
//   private lateinit var binding: FragmentHomeBinding
//   private lateinit var binding: FragmentOperationBinding
//    private lateinit var binding: FragmentSettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }


//    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        adapter = ContianerPagerAdapter(this)
//        binding.fragContainer.adapter = adapter
//        binding.fragContainer.isUserInputEnabled = false
//
//
//
//
//    }

    fun loadCategory(){}

    fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideLoader(){
        binding.progressBar.visibility = View.GONE
    }

}
