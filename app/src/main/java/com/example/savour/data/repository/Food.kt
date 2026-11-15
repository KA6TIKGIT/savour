package com.example.savour.data.repository

data class Food(
    val code: String,
    val name: String,
    val brand: String,
    val imageUrl: String?,
    val thumbUrl: String?,
    val nutriscoreGrade: String?,   // "A".."E" or null
    val ingredients: String?,
    val categories: String?
)