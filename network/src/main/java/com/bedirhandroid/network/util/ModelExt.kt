package com.bedirhandroid.network.util

import com.bedirhandroid.network.model.data.Coin
import com.bedirhandroid.network.model.ui.CoinUiModel

fun Coin.toUiModel(favoriteIds: Set<String>): CoinUiModel {
    return CoinUiModel(
        id = this.uuid,
        name = this.name,
        symbol = this.symbol,
        price = this.price,
        marketCap = this.marketCap,
        change = this.change,
        isFavorite = favoriteIds.contains(this.uuid),
        perDayPrice = this.perDayPrice,
        btcPrice = this.btcPrice,
        color = this.color,
        contractAddresses = this.contractAddresses,
        iconUrl = this.iconUrl,
        listedAt = this.listedAt,
        lowVolume = this.lowVolume,
        rank = this.rank,
        sparkline = this.sparkline,
        tier = this.tier,
        coinRankingUrl = this.coinRankingUrl
    )
}
/*
* val perDayPrice: String,
    val btcPrice: String,
    val change: String,
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
    val id: String,
    val isFavorite: Boolean*/