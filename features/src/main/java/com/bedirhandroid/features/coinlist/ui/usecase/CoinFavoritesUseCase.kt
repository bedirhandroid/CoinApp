package com.bedirhandroid.features.coinlist.ui.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.bedirhandroid.core.base.BaseFlowUseCase
import com.bedirhandroid.core.repository.CoinRepository
import com.bedirhandroid.network.model.CoinUiModel
import com.bedirhandroid.network.repository.Repository
import com.bedirhandroid.network.util.SortOption
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class CoinFavoritesUseCase @Inject constructor(
    private val repo: Repository,
    private val roomRepo: CoinRepository
) :
    BaseFlowUseCase<Pair<SortOption,List<String>?>, PagingData<CoinUiModel>>() {
    override fun execute(params: Pair<SortOption,List<String>?>): Flow<PagingData<CoinUiModel>> {
        return repo.getCoinList(params).map { pagingData ->
            pagingData.map { coin ->
                with(coin) {
                    CoinUiModel(
                        id = uuid,
                        name = name,
                        symbol = symbol,
                        price = price?.toDoubleOrNull()?.let {
                            String.format(Locale.getDefault(),"%.3f", it)
                        },
                        marketCap = marketCap,
                        change = change,
                        isFavorite = uuid?.let { isFavorite(it) },
                        perDayPrice = perDayPrice,
                        btcPrice = btcPrice,
                        coinRankingUrl = coinRankingUrl,
                        contractAddresses = contractAddresses,
                        color = color,
                        iconUrl = iconUrl,
                        listedAt = listedAt,
                        lowVolume = lowVolume,
                        rank = rank,
                        sparkline = sparkline,
                        tier = tier,
                        lowPrice = sparkline?.mapNotNull { it?.toDoubleOrNull()?.let {
                            String.format(Locale.getDefault(),"%.3f", it)
                        } }?.minOrNull()?.toString(),
                        highPrice = sparkline?.mapNotNull { it?.toDoubleOrNull()?.let {
                            String.format(Locale.getDefault(),"%.3f", it)
                        } }?.maxOrNull()?.toString()
                    )
                }
            }
        }
    }
    private suspend fun isFavorite(coinId: String): Boolean {
        return roomRepo.isFavorite(coinId)
    }
}