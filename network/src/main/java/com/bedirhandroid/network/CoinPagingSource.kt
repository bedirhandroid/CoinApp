package com.bedirhandroid.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bedirhandroid.network.api.ApiService
import com.bedirhandroid.network.model.data.Coin
import com.bedirhandroid.network.util.SortOption
import java.io.IOException

class CoinPagingSource (
    private val apiService: ApiService,
    private val sortOption: SortOption,
    private val uuidList : List<String>? = null
) : PagingSource<Int, Coin>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val page = params.key ?: 0
        val limit = 50
        val offset = page * limit

        Log.d("qweqwe", "sortOptions: ${sortOption.apiValue}")
        Log.d("qweqwe", "page: $page, offset: $offset, limit: $limit")

        return try {
            val response = apiService.getCoinList(offset, sortOption.apiValue, uuids = uuidList)

            val coins = response.data.coins
            val totalCoins = response.data.stats.total

            val hasMoreData = totalCoins.let { offset + coins.size < it }
            val nextPage = if (hasMoreData) page + 1 else null

            Log.d("qweqwe", "Fetched: ${coins.size} items, totalCoins: $totalCoins, nextPage: $nextPage")

            LoadResult.Page(
                data = coins,
                prevKey = if (page == 0) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            Log.e("qweqwe", "Error loading data", e)
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}