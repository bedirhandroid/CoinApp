package com.bedirhandroid.features.coinlist.ui.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandroid.core.util.loadImage
import com.bedirhandroid.features.R
import com.bedirhandroid.features.databinding.CoinItemBinding
import com.bedirhandroid.network.model.CoinUiModel

class CoinListAdapter(
    private val onFavoriteClick: (CoinUiModel) -> Unit,
    private val onItemClickListener: (CoinUiModel) -> Unit
) : PagingDataAdapter<CoinUiModel, CoinListAdapter.CoinViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = CoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding, onFavoriteClick, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CoinViewHolder(
        private val binding: CoinItemBinding,
        private val onFavoriteClick: (CoinUiModel) -> Unit,
        private val onItemClickListener: (CoinUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinUiModel) {
            val adapterContext = binding.root.context
            binding.apply {
                root.setOnClickListener { onItemClickListener(coin) }
                with(coin) {
                    tvCoinName.text = name
                    tvCoinPrice.text = price
                    tvCoinSymbol.text = symbol
                    tvCoinChange.text = adapterContext.getString(com.bedirhandroid.core.R.string.coin_change,change,changeAmount)
                    val favIconRes = if (isFavorite == true) {
                        com.bedirhandroid.core.R.drawable.ic_fav_filled
                    } else {
                        com.bedirhandroid.core.R.drawable.ic_fav_no_filled
                    }
                    btnFavorite.setImageDrawable(ContextCompat.getDrawable(adapterContext, favIconRes))

                    btnFavorite.setOnClickListener {
                        onFavoriteClick(this)
                    }

                    iconUrl?.let(imgCoinIcon::loadImage)

                    val changeColor = if (change?.contains("-") == true) {
                        R.color.red
                    } else {
                        R.color.green
                    }
                    tvCoinChange.setTextColor(ContextCompat.getColor(adapterContext, changeColor))
                }
            }
        }
    }

    class CoinDiffCallback : DiffUtil.ItemCallback<CoinUiModel>() {
        override fun areItemsTheSame(oldItem: CoinUiModel, newItem: CoinUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinUiModel, newItem: CoinUiModel): Boolean {
            return oldItem == newItem
        }
    }
}

