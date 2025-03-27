package com.bedirhandroid.network.api

import com.bedirhandroid.network.model.CoinResponse
import retrofit2.http.GET

interface ApiService {
    @GET("coins")
    suspend fun getCoinList() : CoinResponse
}