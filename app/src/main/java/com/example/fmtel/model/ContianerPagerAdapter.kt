package com.example.fmtel.model

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fmtel.MainHostFragment
import com.example.fmtel.fragments.home.HomeFragment
import com.example.fmtel.fragments.operation.OperationFragment
import com.example.fmtel.fragments.SettingsFragment


class ContianerPagerAdapter(fragmentActivity: MainHostFragment) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> OperationFragment()
            2 -> SettingsFragment()


            else -> { // Note the block
                HomeFragment()
            }
        }

    }
}