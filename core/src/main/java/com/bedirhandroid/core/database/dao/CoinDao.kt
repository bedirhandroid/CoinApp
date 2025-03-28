package com.bedirhandroid.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bedirhandroid.core.database.entity.CoinEntity

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinEntity)

    @Query("DELETE FROM favorite_coins WHERE id = :coinId")
    suspend fun deleteById(coinId: String)

    @Query("SELECT * FROM favorite_coins WHERE id = :coinId LIMIT 1")
    suspend fun getCoinById(coinId: String): CoinEntity?

    @Query("SELECT * FROM favorite_coins")
    suspend fun getAllCoins(): List<CoinEntity>
}