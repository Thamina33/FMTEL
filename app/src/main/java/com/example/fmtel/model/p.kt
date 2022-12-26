package com.example.fmtel.model

data class p(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val codes: List<Code>,
        val date: String,
        val id: Int,
        val price: String,
        val product_name: String,
        val quantity: Int,
        val time: String,
        val transaction_id: String,
        val user_id: Int
    ) {
        data class Code(
            val code: String,
            val expiry_date: String,
            val id: Int,
            val serial_number: String
        )
    }
}