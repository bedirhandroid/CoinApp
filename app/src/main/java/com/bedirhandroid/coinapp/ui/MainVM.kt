package com.bedirhandroid.coinapp.ui

import com.bedirhandroid.core.base.BaseViewModel
import com.bedirhandroid.features.coinlist.ui.usecase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val useCase: CoinListUseCase): BaseViewModel() {
    val testValue = "Test Value"
}