package com.bedirhandroid.core.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.Method

fun <T> Class<T>.getBindingMethod(): Method {
    return this.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
}



fun <VM : ViewModel> Class<VM>.getViewModelByLazy(owner: Fragment): VM {
    return owner.createViewModelLazy(this.kotlin, { owner.viewModelStore }).value
}


fun <VM : ViewModel> Class<VM>.getActivityViewModel(owner: AppCompatActivity): VM {
    return ViewModelProvider(owner)[this]
}