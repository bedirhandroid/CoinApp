package com.bedirhandroid.network.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Locale

@Parcelize
data class CoinUiModel(
    val perDayPrice: String? = null,
    val btcPrice: String? = null,
    val change: String? = null,
    val coinRankingUrl: String? = null,
    val color: String? = null,
    val contractAddresses: List<String?>? = null,
    val iconUrl: String? = null,
    val listedAt: Int? = null,
    val lowVolume: Boolean? = null,
    val marketCap: String? = null,
    val name: String? = null,
    val price: String? = null,
    val rank: Int? = null,
    val sparkline: List<String?>? = null,
    val symbol: String? = null,
    val tier: Int? = null,
    val id: String? = null,
    val isFavorite: Boolean? = null,
    val highPrice: String? = null,
    val lowPrice: String? = null
) : Parcelable {

    val changeAmount: String?
        get() {
            val currentPrice = price?.toDoubleOrNull() ?: return null
            val changePercentage = change?.toDoubleOrNull() ?: return null
            val previousPrice = sparkline?.mapNotNull { it?.toDoubleOrNull() }?.lastOrNull() ?: return null
            val changeAmount = (changePercentage / 100) * previousPrice
            return String.format(Locale.getDefault(),"%.2f", changeAmount)
        }
}
