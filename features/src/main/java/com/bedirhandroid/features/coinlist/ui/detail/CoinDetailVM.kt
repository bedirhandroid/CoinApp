package com.bedirhandroid.features.coinlist.ui.detail

import androidx.lifecycle.viewModelScope
import com.bedirhandroid.core.base.BaseViewModel
import com.bedirhandroid.core.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailVM @Inject constructor(
    private val roomRepo: CoinRepository
): BaseViewModel() {

    fun addFavorite(id: String) {
        viewModelScope.launch {
            roomRepo.addToFavorites(id)
        }
    }

    fun removeFavorite(coinId: String) {
        viewModelScope.launch {
            roomRepo.removeFromFavorites(coinId)
        }
    }

}