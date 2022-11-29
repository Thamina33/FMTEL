package com.example.fmtel.fragments.payment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.fmtel.R
import com.example.fmtel.databinding.FragmentPaymentReportragmentBinding
import java.text.DateFormat
import java.text.DateFormatSymbols
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
        return binding.root



    }




    private fun openDatePcker() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)


        val datepickerdialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->

                binding.startDate.text = "$dayOfMonth/$monthOfYear/$year"

            }, y, m, d
        )


        datepickerdialog.datePicker.minDate = System.currentTimeMillis() - 1000

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

                binding.endDate.text = "$dayOfMonth/$monthOfYear/$year"

            }, yy, mm, dd
        )


        datepickerdialog.datePicker.minDate = System.currentTimeMillis() - 1000

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

}