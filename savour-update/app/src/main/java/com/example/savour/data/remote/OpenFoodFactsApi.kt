package com.example.savour.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal const val PRODUCT_FIELDS =
    "code,product_name,brands,image_front_url,image_front_small_url," +
            "nutriscore_grade,nutrition_grades,ingredients_text,categories"

interface OpenFoodFactsApi {

    // The old cgi/search.pl endpoint often returns 503, so search uses the newer search service
    @GET("https://search.openfoodfacts.org/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("page_size") pageSize: Int = 20,
        @Query("fields") fields: String = PRODUCT_FIELDS
    ): SearchResponse

    @GET("api/v2/product/{code}")
    suspend fun getProduct(
        @Path("code") code: String,
        @Query("fields") fields: String = PRODUCT_FIELDS
    ): ProductResponse
}