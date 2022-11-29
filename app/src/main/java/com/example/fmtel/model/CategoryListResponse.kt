package com.example.fmtel.model

data class CategoryListResponse(
    val `data`: List<CategoryItem>,
    val message: String,
    val status: Boolean
) {
    data class CategoryItem(
        val id: Int,
        val name: String
    ) : java.io.Serializable
}