package com.bedirhandroid.features.coinlist.ui.usecase

import com.bedirhandroid.core.base.BaseFlowUseCase
import com.bedirhandroid.network.model.CoinResponse
import com.bedirhandroid.network.repository.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinListUseCase @Inject constructor(private val repo: Repository): BaseFlowUseCase<Unit, CoinResponse>() {
    override fun execute(params: Unit): Flow<CoinResponse> =
        repo.getCoinList().map { it }
    }