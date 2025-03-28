package com.bedirhandroid.network.util

enum class SortOption(val apiValue: String) {
    PRICE("price"),
    MARKET_CAP("marketCap"),
    VOLUME_24H("24hVolume"),
    CHANGE_PERCENTAGE("change"),
    LISTING_DATE("listedAt")
}