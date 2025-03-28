package com.bedirhandroid.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bedirhandroid.core.database.dao.CoinDao
import com.bedirhandroid.core.database.entity.CoinEntity

@Database(entities = [CoinEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}