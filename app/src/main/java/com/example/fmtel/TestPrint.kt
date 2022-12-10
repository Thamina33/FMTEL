package com.example.fmtel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmedelsayed.sunmiprinterutill.PrintMe
import com.example.fmtel.databinding.ActivityTestPrintBinding


class TestPrint : AppCompatActivity() {
    private lateinit var binding: ActivityTestPrintBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val printMe = PrintMe(this)

        binding.test.setOnClickListener {
            printMe.sendViewToPrinter(binding.layout.printImg)
        }

    }
}