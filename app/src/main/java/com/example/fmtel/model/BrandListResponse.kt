package com.example.fmtel.model

data class BrandListResponse(
    val `data`: BrandObj,
    val message: String,
    val status: Boolean
) {


    data class BrandItem(
        val category: String,
        val category_id: Int,
        val id: Int,
        val image: String,
        val name: String
    ): java.io.Serializable
}


data class  BrandObj (
   val brands : List<BrandListResponse.BrandItem> = emptyList()
) : java.io.Serializable