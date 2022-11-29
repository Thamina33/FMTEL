package com.example.fmtel.model

data class PackageListResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val brand: Brand,
        val packages: List<PackageItem>
    ) {
        data class Brand(
            val category: String,
            val category_id: Int,
            val id: Int,
            val image: String,
            val name: String
        )

        data class PackageItem(
            val id: Int,
            val name: String,
            val brand_background_image: String
        ): java.io.Serializable
    }
}