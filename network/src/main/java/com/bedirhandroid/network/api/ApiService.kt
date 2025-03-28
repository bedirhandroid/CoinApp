package com.bedirhandroid.network.api

import com.bedirhandroid.network.model.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("coins")
    suspend fun getCoinList(
        @Query("offset") page: Int,
        @Query("orderBy") sort: String,
        @Query("timePeriod") timePeriod: String = "24h",
        @Query("limit") limit: Int = 50,
        @Query("uuids[]") uuids: List<String>? = null
    ): CoinResponse
}