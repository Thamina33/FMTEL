package com.example.fmtel.model

data class ShopProfileResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val address: String,
        val brand_name: String,
        val city: String,
        val district: String,
        val id: Int,
        val owner_name: String,
        val region: String,
        val registration_date: String,
        val user_id: Int
    ): java.io.Serializable
}