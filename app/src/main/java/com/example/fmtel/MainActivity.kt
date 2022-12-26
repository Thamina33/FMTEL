package com.example.fmtel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

import android.view.View
import androidx.activity.addCallback
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.fmtel.Utils.lastLoginDate
import com.example.fmtel.databinding.ActivityMainBinding
import com.example.fmtel.model.ContianerPagerAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var adapter: ContianerPagerAdapter
    private var doubleBackToExitPressedOnce = false
//   private lateinit var binding: FragmentHomeBinding
//   private lateinit var binding: FragmentOperationBinding
//    private lateinit var binding: FragmentSettingsBinding


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
//            // Back is pressed... Finishing the activity
//
//            if(findNavController(R.id.container_fragment).currentDestination?.id == R.id.homeFragment){
//                triggerDialoguer()
//            }else {
//                findNavController(R.id.container_fragment).popBackStack()
//            }
//
//
//        }

    }

    override fun onBackPressed() {

        val navigationController = findNavController(R.id.container_fragment)
        Log.d("TAG", "onBackPressed: pppppp${navigationController.currentDestination?.id}")

        when (navigationController.currentDestination?.id) {
            R.id.mainHostFragment -> {
                triggerDialoguer()
            }

//            R.id.dashboardFragment -> {
//
//                val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
//                Navigation.findNavController(
//                    this,
//                    R.id.nav_host_container
//                ).navigate(R.id.choiceFragment, null, navOptions)
//            }
            else -> {
                super.onBackPressed()
            }
        }

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

    private fun triggerDialoguer() {

        //var dialog : AlertDialog =
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton(
            getString(android.R.string.ok)
        ) { dialog, _ ->
            dialog.cancel()
            moveTaskToBack(true);
            exitProcess(-1)
        }
        alertDialogBuilder.setNegativeButton("no", null)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }



}
