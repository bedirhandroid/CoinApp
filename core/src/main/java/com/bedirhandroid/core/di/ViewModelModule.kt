package com.bedirhandroid.core.di

import com.bedirhandroid.core.base.BaseViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideBaseViewModel(): BaseViewModel {
        return BaseViewModel()
    }
}