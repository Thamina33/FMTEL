package com.example.fmtel.model

data class ProductListResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
): java.io.Serializable {
    data class Data(
        val `package`: Package,
        val products: List<ProductItem>,
        val brand: Brand,
    ) : java.io.Serializable {
        data class Package(
            val id: Int,
            val name: String,
            val brand_background_image: String
        ) :java.io.Serializable

        data class ProductItem(
            //val code: String,
            val id: Int,
            val image: String,
            val name: String,
            val package_id: Int,
            val price: String,
            var qty : Int = 1,
            var stock : Int  = 0
        ): java.io.Serializable
    }
}