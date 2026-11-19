package com.example.savour.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Response of the newer search service (search.openfoodfacts.org)
@Serializable
data class SearchResponse(
    val hits: List<SearchHitDto> = emptyList()
)

// Same fields as ProductDto, except the search service returns brands as a list
@Serializable
data class SearchHitDto(
    val code: String? = null,
    @SerialName("product_name") val productName: String? = null,
    val brands: List<String> = emptyList(),
    @SerialName("image_front_url") val imageUrl: String? = null,
    @SerialName("image_front_small_url") val thumbUrl: String? = null,
    @SerialName("nutriscore_grade") val nutriscoreGrade: String? = null,
    @SerialName("nutrition_grades") val nutritionGrades: String? = null,
    @SerialName("ingredients_text") val ingredients: String? = null,
    val categories: String? = null
) {
    fun toProductDto() = ProductDto(
        code = code,
        productName = productName,
        brands = brands.joinToString(","),
        imageUrl = imageUrl,
        thumbUrl = thumbUrl,
        nutriscoreGrade = nutriscoreGrade,
        nutritionGrades = nutritionGrades,
        ingredients = ingredients,
        categories = categories
    )
}

@Serializable
data class ProductResponse(
    val product: ProductDto? = null
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