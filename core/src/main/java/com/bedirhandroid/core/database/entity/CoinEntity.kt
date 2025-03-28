package com.bedirhandroid.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_coins")
data class CoinEntity(
    @PrimaryKey val id: String
)