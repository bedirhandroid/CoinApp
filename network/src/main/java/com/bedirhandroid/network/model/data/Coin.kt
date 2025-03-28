package com.bedirhandroid.network.model.data

import kotlinx.serialization.SerialName

data class Coin(
    @SerialName("24hVolume")
    val perDayPrice: String? = null,
    val btcPrice: String? = null,
    val change: String? = null,
    @SerialName("coinrankingUrl")
    val coinRankingUrl: String? = null,
    val color: String? = null,
    val contractAddresses: List<String?>? = null,
    val iconUrl: String? = null,
    val listedAt: Int? = null,
    val lowVolume: Boolean? = null,
    val marketCap: String? = null,
    val name: String? = null,
    val price: String? = null,
    val rank: Int,
    val sparkline: List<String?>? = null,
    val symbol: String? = null,
    val tier: Int? = null,
    val uuid: String? = null
)