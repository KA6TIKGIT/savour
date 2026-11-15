package com.example.savour.data.repository

import com.example.savour.data.local.SavedFood
import com.example.savour.data.local.SavedFoodDao
import com.example.savour.data.remote.OpenFoodFactsApi
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val api: OpenFoodFactsApi,
    private val dao: SavedFoodDao
) {

    // ---- network ----
    suspend fun search(query: String): List<Food> =
        api.searchProducts(query).products.mapNotNull { it.toFood() }

    suspend fun getFood(code: String): Food? =
        api.getProduct(code).product?.toFood()

    // ---- local ----
    fun observeSaved(code: String): Flow<SavedFood?> = dao.observe(code)

    fun observeFavorites(): Flow<List<SavedFood>> = dao.observeFavorites()

    suspend fun toggleFavorite(food: Food) {
        val row = dao.get(food.code) ?: food.toSaved()
        dao.upsert(row.copy(isFavorite = !row.isFavorite))
    }

    suspend fun saveReview(food: Food, rating: Int, review: String) {
        val row = dao.get(food.code) ?: food.toSaved()
        dao.upsert(row.copy(rating = rating, review = review))
    }
}