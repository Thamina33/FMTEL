package com.example.fmtel.model

data class BalanceResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val available: String,
        val current: String,
        val reserved: String
    )
}