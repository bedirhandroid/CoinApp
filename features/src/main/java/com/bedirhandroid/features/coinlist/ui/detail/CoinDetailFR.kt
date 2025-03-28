package com.bedirhandroid.features.coinlist.ui.detail

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bedirhandroid.core.base.BaseFragment
import com.bedirhandroid.core.util.navigateBack
import com.bedirhandroid.features.R
import com.bedirhandroid.features.databinding.CoinDetailBinding
import com.bedirhandroid.network.model.CoinUiModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class CoinDetailFR: BaseFragment<CoinDetailBinding,CoinDetailVM>() {
    private var coin : CoinUiModel? = null
    override fun initView() {
        coin = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("data", CoinUiModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("data")
        }
        binding.apply {
            tvSymbol.text = coin?.symbol ?: "N/A"
            tvCoinName.text = coin?.name ?: "N/A"
            tvCurrentPrice.text = getString(com.bedirhandroid.core.R.string.start_with_usd,coin?.price)
            tvChangeRate.text = getString(com.bedirhandroid.core.R.string.coin_change,coin?.change,coin?.changeAmount)
            tvHighValue.text = getString(com.bedirhandroid.core.R.string.start_with_usd, coin?.highPrice)
            tvLowValue.text = getString(com.bedirhandroid.core.R.string.start_with_usd, coin?.lowPrice)
            updateFavoriteUI(coin?.isFavorite ?: false)
        }
        coin?.sparkline?.let { setupChart(binding.chart, it) }
    }

    override fun initListeners() {
        binding.apply {
            btnFavorite.setOnClickListener {
                toggleFavorite()
            }
            btnBack.setOnClickListener {
                navigateBack()
            }
        }

    }

    override fun initObservers() {

    }

    private fun toggleFavorite() {
        coin?.let {
            val isNowFavorite = !(it.isFavorite ?: false)
            val updatedCoin = it.copy(isFavorite = isNowFavorite)
            coin = updatedCoin
            if (isNowFavorite) {
                updatedCoin.id?.let { it1 -> viewModel.addFavorite(it1) }
            } else {
                viewModel.removeFavorite(updatedCoin.id ?: "")
            }
            updateFavoriteUI(isNowFavorite)
        }
    }

    private fun setupChart(chart: LineChart, dataList: List<String?>) {
        val floatList = dataList.mapNotNull { it?.toFloatOrNull() }

        val entries = floatList.mapIndexed { index, value ->
            Entry(index.toFloat(), value)
        }

        val dataSet = LineDataSet(entries, "Veri Grafiği").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.RED)
        }
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            axisMinimum = 0f
            axisMaximum = 24f
            labelCount = 25
            granularity = 1f
        }

        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = true

        chart.data = LineData(dataSet)
        chart.invalidate()

        chart.data = LineData(dataSet)
        chart.invalidate()
    }


    private fun updateFavoriteUI(isFavorite: Boolean) {
        when(isFavorite) {
            true -> com.bedirhandroid.core.R.drawable.ic_fav_filled
            else -> com.bedirhandroid.core.R.drawable.ic_fav_no_filled
        }.also { binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), it)) }
    }
}