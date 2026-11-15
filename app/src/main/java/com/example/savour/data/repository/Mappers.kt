package com.example.savour.data.repository

import com.example.savour.data.local.SavedFood
import com.example.savour.data.remote.ProductDto

fun ProductDto.toFood(): Food? {
    val barcode = code?.takeIf { it.isNotBlank() } ?: return null
    return Food(
        code = barcode,
        name = productName?.takeIf { it.isNotBlank() } ?: "Unnamed product",
        brand = brands?.substringBefore(",")?.trim().orEmpty(),
        imageUrl = imageUrl,
        thumbUrl = thumbUrl ?: imageUrl,
        nutriscoreGrade = (nutriscoreGrade ?: nutritionGrades)
            ?.takeIf { it.length == 1 }
            ?.uppercase(),
        ingredients = ingredients?.takeIf { it.isNotBlank() },
        categories = categories?.takeIf { it.isNotBlank() }
    )
}

fun Food.toSaved() = SavedFood(
    code = code,
    name = name,
    brand = brand,
    imageUrl = thumbUrl ?: imageUrl,
    nutriscoreGrade = nutriscoreGrade
)