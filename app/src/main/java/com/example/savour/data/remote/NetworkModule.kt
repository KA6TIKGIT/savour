package com.example.savour.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NetworkModule {

    private val json = Json { ignoreUnknownKeys = true }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Savour/1.0 (kartikmeti2006@gmail.com)")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: OpenFoodFactsApi = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.org/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(OpenFoodFactsApi::class.java)
}