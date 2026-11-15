package com.example.savour

import android.app.Application
import com.example.savour.data.local.AppDatabase
import com.example.savour.data.remote.NetworkModule
import com.example.savour.data.repository.FoodRepository

class SavourApp : Application() {
    val repository by lazy {
        FoodRepository(NetworkModule.api, AppDatabase.get(this).savedFoodDao())
    }
}