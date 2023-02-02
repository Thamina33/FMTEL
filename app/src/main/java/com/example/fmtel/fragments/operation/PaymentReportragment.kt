package com.example.fmtel.fragments.operation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.MainActivity
import com.example.fmtel.databinding.FragmentPaymentReportragmentBinding
import com.example.fmtel.model.ReportbyDatesResponse
import com.example.fmtel.networking.ApiProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PaymentReportragment : Fragment() {
    private lateinit var binding: FragmentPaymentReportragmentBinding

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentPaymentReportragmentBinding.inflate(inflater, container,false)
        return binding.root





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printMe = PrintMe(binding.root.context)
        binding.startDate.setOnClickListener {
            openDatePcker(

            )
        }
        binding.endDate.setOnClickListener {
            endopenDatePcker(
            )
        }
        binding.startTime.setOnClickListener {
            getTime(requireContext())
        }
        binding.endTime.setOnClickListener {
            endgetTime(requireContext())
        }

        //current date
        val sdf = SimpleDateFormat("dd.MM.yyyyy 'Time:' HH:mm:ss")
        val currentDateandTime = sdf.format(Date())
        binding.currentDate.text = currentDateandTime

            binding.reportBtn.setOnClickListener {
            val start_d = binding.startDate.text.toString()
            val end_d = binding.endDate.text.toString()
            loadReport(start_d, end_d)

        }
        binding.printBtn.setOnClickListener {

            printMe.sendTextToPrinter("FM TEL\nPayment Report\n\n" , 32f, true , false , 0)
            printMe.sendViewToPrinter(binding.reportCard)
            printMe.sendTextToPrinter("Powered By FM TEL\nDeveloped By SPINNER TECH\n\n" , 24f, true , false , 1)


            findNavController().popBackStack()
        }

    }




    private fun openDatePcker() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        val datepickerdialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                var startmon = monthOfYear + 1

                binding.startDate.text = "$year/$startmon/$dayOfMonth"

            }, y, m, d
        )


       // datepickerdialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datepickerdialog.show()
    }

    private fun endopenDatePcker() {
        val cal = Calendar.getInstance()
        val yy = cal.get(Calendar.YEAR)
        val mm = cal.get(Calendar.MONTH)
        val dd = cal.get(Calendar.DAY_OF_MONTH)


        val datepickerdialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                var mon = monthOfYear + 1
                binding.endDate.text = "$year/$mon/$dayOfMonth"

            }, yy, mm, dd
        )


       // datepickerdialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datepickerdialog.show()
    }

    fun getTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

           binding.startTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }


        TimePickerDialog(context, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
            .show()

    }

    fun endgetTime(context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.endTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }


        TimePickerDialog(context, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
            .show()

    }

    private fun loadReport(startDate: String, endDate: String) {
        (activity as MainActivity).showLoader()
        val  reportcall  = ApiProvider.dataApi.getReportbyDates(
            startDate = startDate , endDate = endDate
        )
        reportcall.enqueue(object : Callback<ReportbyDatesResponse?> {
            override fun onResponse(
                call: Call<ReportbyDatesResponse?>,
                response: Response<ReportbyDatesResponse?>
            ) {
                // binding.pbar.visibility = View.GONE


                (activity as MainActivity).hideLoader()
                if (response.isSuccessful && response.code() == 200) {
                    val resp = response.body()

                    if (resp != null) {

                       // profileData = resp.data

                        Log.d("TAG", "onResponse: ${resp.message}")

                        binding.reportCard.visibility = View.VISIBLE
                        binding.printBtn.visibility = View.VISIBLE
                        binding.tba.text= resp.data.tba
                        binding.totalamount.text=resp.data.sale_amount.toString()
                        binding.currentBalance.text= resp.data.balance



                    }


                } else if (response.code() == 401) {
                    //Helper.showErrorMsg("Server Error ${response.code()}", requireContext())
                    Toast.makeText(
                        requireContext(),
                        "Token Invalid",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (response.code() == 404) {
                    binding.reportCard.visibility = View.INVISIBLE
                    binding.printBtn.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireContext(),
                        "No sales available within this period",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        "Input Data Properly",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


            override fun onFailure(call: Call<ReportbyDatesResponse?>, t: Throwable) {

            }

        })


    }

}