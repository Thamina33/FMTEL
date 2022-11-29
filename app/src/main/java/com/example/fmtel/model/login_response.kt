package com.example.fmtel.model

data class login_response(
    val `data`: Data?,
    val message: String,
    val status: Boolean
)

data class Data(
    val token: String

)