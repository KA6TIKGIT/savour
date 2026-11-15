package com.example.savour.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedFoodDao {

    @Query("SELECT * FROM saved_foods WHERE code = :code")
    fun observe(code: String): Flow<SavedFood?>

    @Query("SELECT * FROM saved_foods WHERE code = :code")
    suspend fun get(code: String): SavedFood?

    @Query("SELECT * FROM saved_foods WHERE isFavorite = 1 ORDER BY name")
    fun observeFavorites(): Flow<List<SavedFood>>

    @Upsert
    suspend fun upsert(food: SavedFood)
}