package com.bedirhandroid.core.repository

import com.bedirhandroid.core.database.dao.CoinDao
import com.bedirhandroid.core.database.entity.CoinEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinDao: CoinDao
) {
    suspend fun addToFavorites(id: String) {
        coinDao.insert(CoinEntity(id = id))
    }

    suspend fun removeFromFavorites(id: String) {
        coinDao.deleteById(id)
    }

    suspend fun isFavorite(coinId: String): Boolean {
        return coinDao.getCoinById(coinId) != null
    }

    suspend fun getFavoriteIds(): List<String> {
        return coinDao.getAllCoins().map { it.id }
    }
}