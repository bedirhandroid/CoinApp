package com.bedirhandroid.features.coinlist.ui.list

import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bedirhandroid.core.base.BaseFragment
import com.bedirhandroid.core.util.navigateWithBundleTo
import com.bedirhandroid.features.R
import com.bedirhandroid.features.coinlist.ui.pagination.CoinListAdapter
import com.bedirhandroid.features.databinding.FrCoinListBinding
import com.bedirhandroid.network.model.CoinUiModel
import com.bedirhandroid.network.util.SortOption
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CoinListFR : BaseFragment<FrCoinListBinding, CoinListVM>() {
    private lateinit var coinListAdapter: CoinListAdapter

    override fun initView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        coinListAdapter = CoinListAdapter(::toggleFavorite, ::navigateToDetail)
        binding.recyclerViewCoins.apply {
            adapter = coinListAdapter
        }

        coinListAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> handleLoadingState(true)
                is LoadState.NotLoading -> handleLoadingState(false)
                is LoadState.Error -> showError("ERROR")
            }
        }
    }

    private fun toggleFavorite(data: CoinUiModel) {
        viewModel.toggleFavorite(data)
    }

    private fun navigateToDetail(data: CoinUiModel) {
        navigateWithBundleTo(
            R.id.action_coinListFR_to_coinDetailFR,
            bundle = bundleOf(
                "data" to data
            )
        )
    }

    override fun initListeners() {
        binding.apply {
            spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedSort = when (position) {
                        0 -> SortOption.PRICE
                        1 -> SortOption.MARKET_CAP
                        2 -> SortOption.VOLUME_24H
                        3 -> SortOption.CHANGE_PERCENTAGE
                        4 -> SortOption.LISTING_DATE
                        else -> SortOption.PRICE
                    }
                    viewModel.updateSortOption(selectedSort)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun observePagingData() {
        lifecycleScope.launch {
            viewModel.coinList.collectLatest { pagingData ->
                coinListAdapter.submitData(pagingData)
            }
        }
    }


    override fun initObservers() {
        observePagingData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCoins()
    }
}