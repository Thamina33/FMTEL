package com.example.fmtel.Utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Helper {
    companion object {
        fun getCurrentDate() : String {
           // var date = ""

            val formatter = SimpleDateFormat("dd-mm-yyyy")
            val date = Date()
            val current = formatter.format(date)



            return current


        }

        fun getCurrentTimeInMilli(): Long {
            val tiem = System.currentTimeMillis()

            return tiem
        }
    }

}