package com.bedirhandroid.features.coinlist.ui.list

import com.bedirhandroid.core.base.BaseViewModel
import com.bedirhandroid.features.coinlist.ui.usecase.CoinListUseCase
import com.bedirhandroid.network.model.CoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CoinListVM @Inject constructor(
    private val coinListUseCase: CoinListUseCase
) : BaseViewModel() {

    private val _coinList = MutableStateFlow<CoinResponse?>(null)
    val coinList: StateFlow<CoinResponse?> = _coinList.asStateFlow()

    init {
        getCoinList()
    }

    private fun getCoinList() {
        coinListUseCase.invoke(Unit).collectFlow(_coinList)
    }
}