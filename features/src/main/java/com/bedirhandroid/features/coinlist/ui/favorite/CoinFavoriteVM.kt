package com.bedirhandroid.features.coinlist.ui.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.bedirhandroid.core.base.BaseViewModel
import com.bedirhandroid.core.repository.CoinRepository
import com.bedirhandroid.features.coinlist.ui.usecase.CoinFavoritesUseCase
import com.bedirhandroid.network.model.ui.CoinUiModel
import com.bedirhandroid.network.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinFavoriteVM @Inject constructor(
    private val favoritesUseCase: CoinFavoritesUseCase,
    private val coinRepository: CoinRepository
) : BaseViewModel() {

    private val _favoriteCoins = MutableStateFlow<PagingData<CoinUiModel>>(PagingData.empty())
    val favoriteCoins: StateFlow<PagingData<CoinUiModel>> = _favoriteCoins.asStateFlow()

    var currentSortOption: SortOption = SortOption.PRICE

    init {
        fetchFavoriteCoins(currentSortOption)
    }

    fun fetchFavoriteCoins(sortOption: SortOption) {
        viewModelScope.launch {
            val favoriteIds = coinRepository.getFavoriteIds()
            if (favoriteIds.isNotEmpty()) {
                favoritesUseCase.invoke(Pair(sortOption,favoriteIds))
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _favoriteCoins.value = pagingData
                    }
            }
        }
    }


    fun toggleFavorite(coinId: String) {
        viewModelScope.launch {
            coinRepository.removeFromFavorites(coinId)

            _favoriteCoins.value = _favoriteCoins.value.filter { pagingData ->
                pagingData.id != coinId
            }
        }
    }


    fun updateSortOption(sortOption: SortOption) {
        if (currentSortOption != sortOption) {
            currentSortOption = sortOption
            fetchFavoriteCoins(sortOption)
        }
    }
}
