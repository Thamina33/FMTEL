package com.example.fmtel.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ContentInfoCompat.Flags
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fmtel.R
import com.example.fmtel.SharedPrefManager
import com.example.fmtel.SigninActivity
import com.example.fmtel.databinding.FragmentChangePinCodeBinding
import com.example.fmtel.model.PinUpdate
import com.example.fmtel.model.ShopProfileResponse
import com.example.fmtel.model.login_response
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePinCodeFragment : Fragment() {
    private lateinit var binding: FragmentChangePinCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangePinCodeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val updatePin = arguments?.getSerializable("LoginData") as login_response?


        binding.UpdateBtn.setOnClickListener {
            val pass = binding.currentPass.text.toString()
            val newPass = binding.conPass.text.toString()
            val conPass = binding.conPass.text.toString()
            if (pass.isEmpty()) {
                binding.currentPass.error = "Can't Be Empty"
            }
            else if (newPass.isEmpty()) {
                binding.newPass.error = "Can't Be Empty"
            }
            else if (conPass.isEmpty()) {
                binding.conPass.error = "Can't Be Empty"
            }

            else if (newPass != conPass){
               binding.conPass.error = "Not Same"
            }
            else{
                cngPass(pass, newPass , conPass)
            }

        }
        binding.cancleBtn.setOnClickListener {
            findNavController().popBackStack()

        }

    }
    private fun cngPass(pass: String,newPass: String,conPass: String) {
        val passCall = ApiProvider.dataApi.pinUpdate(

            password = newPass, old_password = pass.toInt() , password_confirmation = conPass

        )



        passCall.enqueue(object : Callback<PinUpdate?> {
            override fun onResponse(
                call: Call<PinUpdate?>,
                response: Response<PinUpdate?>
            ) {
                // binding.pbar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                        Toast.makeText(
                            requireContext(),
                            "Pin Updated Please Login with new PIN CODE",
                            Toast.LENGTH_LONG
                        ).show()
                        goForogin()

                    }


                } else if (response.isSuccessful && response.code() == 403) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Old Pin Code doesn't match",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else  {
                    Toast.makeText(
                        requireContext(),
                        "Server Error" ,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }


            override fun onFailure(call: Call<PinUpdate?>, t: Throwable) {

            }

        })


    }
    fun  goForogin(){
        SharedPrefManager.nukeAllData()
        requireActivity().run{
            startActivity(Intent(this, SigninActivity::class.java).apply {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })

            finish()
        }
    }

}