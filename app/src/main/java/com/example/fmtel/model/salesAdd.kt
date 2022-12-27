package com.example.fmtel.model

data class salesAdd(
    val message: String,
    val `data`: SaleItem,
    val status: Boolean
) {
    data class SaleItem(
        val codes: List<Code>,
        val date: String,
        val id: Int,
        val price: String,
        val product_name: String,
        val quantity: String,
        val time: String,
        val brand_name: String,
        val recharge_message: String,
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