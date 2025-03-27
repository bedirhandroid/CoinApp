package com.bedirhandroid.features.coinlist.ui.list

import android.util.Log
import com.bedirhandroid.core.base.BaseFragment
import com.bedirhandroid.core.base.UiState
import com.bedirhandroid.features.databinding.FrCoinListBinding
import com.bedirhandroid.network.model.Coin
import com.bedirhandroid.network.model.CoinResponse

class CoinListFR : BaseFragment<FrCoinListBinding, CoinListVM>() {
    override fun initView() {
    }

    override fun initListeners() {}

    override fun initObservers() {
        collectFlow(viewModel.coinList) { data ->
            if (data != null) {
                initUi(data)
            }
        }
    }


    private fun initUi(data: CoinResponse) {
        Log.d("qweqwe", "initUi: ${data.data.coins.map { it.name }}")
    }
}