package com.bedirhandroid.features.coinlist.ui.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.bedirhandroid.core.base.BaseViewModel
import com.bedirhandroid.core.database.entity.CoinEntity
import com.bedirhandroid.core.repository.CoinRepository
import com.bedirhandroid.features.coinlist.ui.usecase.CoinListUseCase
import com.bedirhandroid.network.model.CoinUiModel
import com.bedirhandroid.network.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListVM @Inject constructor(
    private val coinListUseCase: CoinListUseCase,
    private val coinRepository: CoinRepository
) : BaseViewModel() {

    private var currentSortOption: SortOption = SortOption.PRICE

    private val _coinList = MutableStateFlow<PagingData<CoinUiModel>>(PagingData.empty())
    val coinList: StateFlow<PagingData<CoinUiModel>> = _coinList.asStateFlow()

    fun loadCoins() {
        viewModelScope.launch {
            coinListUseCase.invoke(Pair(currentSortOption, null))
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _coinList.value = pagingData
                }
        }
    }

    fun toggleFavorite(coin: CoinUiModel) {
        viewModelScope.launch {
            val updatedCoin = coin.copy(isFavorite = !coin.isFavorite)

            if (updatedCoin.isFavorite == true) {
                coin.id?.let {
                    coinRepository.addToFavorites(
                        it
                    )
                }
            } else {
                coin.id?.let {
                    coinRepository.removeFromFavorites(
                        it
                    )
                }
            }

            _coinList.value = _coinList.value.map { oldCoin ->
                if (oldCoin.id == updatedCoin.id) updatedCoin else oldCoin
            }
        }
    }

    fun updateSortOption(sortOption: SortOption) {
        if (currentSortOption != sortOption) {
            currentSortOption = sortOption
            loadCoins()
        }
    }
}

