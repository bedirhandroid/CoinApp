package com.bedirhandroid.network.model

import kotlinx.serialization.SerialName

data class Coin(
    @SerialName("24hVolume")
    val perDayPrice: String,
    val btcPrice: String,
    val change: String,
    @SerialName("coinrankingUrl")
    val coinRankingUrl: String,
    val color: String,
    val contractAddresses: List<String>,
    val iconUrl: String,
    val listedAt: Int,
    val lowVolume: Boolean,
    val marketCap: String,
    val name: String,
    val price: String,
    val rank: Int,
    val sparkline: List<String>,
    val symbol: String,
    val tier: Int,
    val uuid: String
)