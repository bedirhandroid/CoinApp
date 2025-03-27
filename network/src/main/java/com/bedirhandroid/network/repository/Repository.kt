package com.bedirhandroid.network.repository

import com.bedirhandroid.network.api.ApiService
import com.bedirhandroid.network.model.CoinResponse
import jakarta.inject.Inject
import kotlinx.coroutines.flow.flow

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getCoinList() = flow { emit(apiService.getCoinList()) }
}