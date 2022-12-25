package com.example.fmtel.model

data class dailySattlementResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val date: String,
        val time: String,
        val transaction_id: String,
        val user_id: String,
        val id: Int,
        val price: String,
        val quantity: Int,
        val product_code: String,
        val product_name: String,
        val serial_no: String,
        val expiry_date: String,
    ): java.io.Serializable {
        data class Product(
            val code: String,
            val id: Int,
            val image: String,
            val name: String,
            val package_id: Int,
            val price: String?
        ): java.io.Serializable
    }
}



data class DailySalesResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val date: String,
        val id: Int,
        val price: String,
        val product: String,
        val quantity: Int,
        val `package` : String
    ): java.io.Serializable
}