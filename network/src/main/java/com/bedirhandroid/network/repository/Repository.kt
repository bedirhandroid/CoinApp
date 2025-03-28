package com.bedirhandroid.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bedirhandroid.core.repository.CoinRepository
import com.bedirhandroid.network.CoinPagingSource
import com.bedirhandroid.network.api.ApiService
import com.bedirhandroid.network.model.Coin
import com.bedirhandroid.network.util.SortOption
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val apiService: ApiService,
) {
    fun getCoinList(request: Pair<SortOption,List<String>?>) : Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CoinPagingSource(apiService,request.first, request.second) }
        ).flow
    }
}