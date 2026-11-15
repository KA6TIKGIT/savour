package com.example.savour.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal const val PRODUCT_FIELDS =
    "code,product_name,brands,image_front_url,image_front_small_url," +
            "nutriscore_grade,nutrition_grades,ingredients_text,categories"

interface OpenFoodFactsApi {

    @GET("cgi/search.pl")
    suspend fun searchProducts(
        @Query("search_terms") query: String,
        @Query("search_simple") simple: Int = 1,
        @Query("action") action: String = "process",
        @Query("json") json: Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("fields") fields: String = PRODUCT_FIELDS
    ): SearchResponse

    @GET("api/v2/product/{code}")
    suspend fun getProduct(
        @Path("code") code: String,
        @Query("fields") fields: String = PRODUCT_FIELDS
    ): ProductResponse
}