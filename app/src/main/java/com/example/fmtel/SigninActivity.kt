package com.example.fmtel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.fmtel.R
import com.example.fmtel.Utils.Helper
import com.example.fmtel.Utils.lastLoginDate
import com.example.fmtel.Utils.userKey
import com.example.fmtel.databinding.ActivityMainBinding
import com.example.fmtel.databinding.ActivitySigninBinding
import com.example.fmtel.model.login_response
import com.example.fmtel.networking.ApiProvider
import com.example.fmtel.networking.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLog.setOnClickListener {
            val ph = binding.loginId.text.toString()
            val pass = binding.pinCode.text.toString()

            if (ph.isEmpty()) {
                binding.loginId.error = "Can't Be Empty"
            } else if (pass.isEmpty()) {
                binding.pinCode.error = "Can't Be Empty"
            } else {
                loginUser(ph, pass)
            }

        }



//        if(SharedPrefManager.getToken()!= null){
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }  last date != current date -> login

    }

    override fun onStart() {
        super.onStart()
        checkForLogin()
    }

    fun checkForLogin(){
        val lastLoginDate = SharedPrefManager.get(lastLoginDate) as String?
        if(lastLoginDate == null || lastLoginDate.isNullOrEmpty()){
            // this is the first login
            // nothing
        }else {

            if(Helper.getCurrentDate() == lastLoginDate){
                goToHome()
            }

        }

    }

    private fun loginUser(email: String, pass: String) {
        binding.progressBar.visibility = View.VISIBLE

        val loginCall = ApiProvider.dataApi.user_login(
            email, pass
        )



        loginCall.enqueue(object : Callback<login_response?>

        {


            override fun onResponse(

                call: Call<login_response?>,
                response: Response<login_response?>
            ) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()
                    Toast.makeText(
                        applicationContext,
                        "Login Successful",
                        Toast.LENGTH_LONG
                    ).show()
                    if (resp != null) {

                        SharedPrefManager.put(resp.data , userKey)
                        SharedPrefManager.put(Helper.getCurrentDate() , lastLoginDate)

                       goToHome()

                    }


                }
                else if ( response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        applicationContext,
                        "Invalid PINCODE, Please input PIN CODE Correctly",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else if ( response.code() == 403) {
                    Toast.makeText(
                        applicationContext,
                        "Invalid Username, Please input Username Correctly",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    Toast.makeText(
                        applicationContext,
                        "Invalid Username or PINCODE",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }


            override fun onFailure(call: Call<login_response?>, t: Throwable) {

            }

        })


    }

    fun  goToHome(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideLoader(){
        binding.progressBar.visibility = View.GONE
    }

}






