package com.bedirhandroid.features.coinlist.ui.favorite

import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bedirhandroid.core.base.BaseFragment
import com.bedirhandroid.core.util.navigateWithBundleTo
import com.bedirhandroid.features.R
import com.bedirhandroid.features.coinlist.ui.pagination.CoinListAdapter
import com.bedirhandroid.features.databinding.CoinFavoriteBinding
import com.bedirhandroid.network.model.CoinUiModel
import com.bedirhandroid.network.util.SortOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinFavoriteFR : BaseFragment<CoinFavoriteBinding, CoinFavoriteVM>() {

    private lateinit var favoriteAdapter: CoinListAdapter
    override fun initView() {
        setupRecyclerView()
        observeFavoriteCoins()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = CoinListAdapter(
            onFavoriteClick = { it.id?.let { it1 -> toggleFavorite(it1) } },
            onItemClickListener = ::navigateToDetail
        )
        binding.rvFavorites.apply {
            adapter = favoriteAdapter
        }

        favoriteAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> handleLoadingState(true)
                is LoadState.NotLoading -> handleLoadingState(false)
                is LoadState.Error -> showError("ERROR")
            }
        }
    }

    private fun toggleFavorite(data: String) {
        viewModel.toggleFavorite(data)
    }

    private fun navigateToDetail(data: CoinUiModel) {
        navigateWithBundleTo(
            R.id.action_coinFavoriteFR_to_coinDetailFR,
            bundle = bundleOf(
                "data" to data
            )
        )
    }

    override fun initObservers() {
        observeFavoriteCoins()
    }

    private fun observeFavoriteCoins() {
        lifecycleScope.launch {
            viewModel.favoriteCoins.collectLatest { pagingData ->
                //binding.tvEmptyFavorites.isVisible = pagingData.isEmpty()
                favoriteAdapter.submitData(pagingData)
            }
        }
    }

    override fun initListeners() {
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSort = SortOption.entries.getOrNull(position) ?: SortOption.PRICE
                viewModel.updateSortOption(selectedSort)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteCoins(viewModel.currentSortOption)
    }
}
