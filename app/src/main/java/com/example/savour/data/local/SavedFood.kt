package com.example.savour.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "saved_foods")
data class SavedFood(
    @PrimaryKey val code: String,          // barcode
    val name: String,
    val brand: String,
    val imageUrl: String?,
    val nutriscoreGrade: String?,
    val isFavorite: Boolean = false,
    val rating: Int = 0,                   // 0 = not rated, otherwise 1..5
    val review: String = ""
)