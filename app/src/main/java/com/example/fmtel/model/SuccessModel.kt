package com.example.fmtel.model

import android.service.autofill.UserData


data class SuccessModel(
    val error: Boolean = false,
    val msg: String = ""
)
data class UserSuccessModel(
    val error: Boolean = false,
    val msg: String = "",
   // val data: userResp.User ?= null
)