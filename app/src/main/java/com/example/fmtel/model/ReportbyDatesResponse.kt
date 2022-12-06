package com.example.fmtel.model

data class ReportbyDatesResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val balance: String,
        val end_date: String,
        val sale_amount: Int,
        val start_date: String,
        val tba: String
    )
}