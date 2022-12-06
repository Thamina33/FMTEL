package com.example.fmtel.model

data class dailySattlementResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val date: String,
        val id: Int,
        val price: String,
        val product: Product,
        val quantity: Int
    ): java.io.Serializable {
        data class Product(
            val code: String,
            val id: Int,
            val image: String,
            val name: String,
            val package_id: Int,
            val price: String
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