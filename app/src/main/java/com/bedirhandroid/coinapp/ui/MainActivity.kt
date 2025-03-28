package com.bedirhandroid.coinapp.ui

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bedirhandroid.coinapp.R
import com.bedirhandroid.coinapp.databinding.ActivityMainBinding
import com.bedirhandroid.core.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    override fun initView() {
        setupBottomBar()
    }

    override fun initListeners() {}

    override fun initObservers() {}

    private fun setupBottomBar() {
        val navController = findNavController(R.id.nav_host_fragment_container)
        binding.apply {
            binding.bottomNavigation.setupWithNavController(navController)
        }
    }
}