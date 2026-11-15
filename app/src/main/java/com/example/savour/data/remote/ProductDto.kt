package com.example.savour.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val products: List<ProductDto> = emptyList()
)

@Serializable
data class ProductDto(
    val code: String? = null,
    @SerialName("product_name") val productName: String? = null,
    val brands: String? = null,
    @SerialName("image_front_url") val imageUrl: String? = null,
    @SerialName("image_front_small_url") val thumbUrl: String? = null,
    @SerialName("nutriscore_grade") val nutriscoreGrade: String? = null,
    @SerialName("nutrition_grades") val nutritionGrades: String? = null,
    @SerialName("ingredients_text") val ingredients: String? = null,
    val categories: String? = null
)