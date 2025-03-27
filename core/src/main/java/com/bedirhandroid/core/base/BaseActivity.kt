package com.bedirhandroid.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.bedirhandroid.core.util.getActivityViewModel
import com.bedirhandroid.core.util.getBindingMethod
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : HiltActivity() {
    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set

    private val type = javaClass.genericSuperclass as ParameterizedType
    private val bindingClass = type.actualTypeArguments[0] as Class<VB>
    private val viewModelClass = type.actualTypeArguments[1] as Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGenericBinding()
        viewModel = viewModelClass.getActivityViewModel(this)
        initView()
        initListeners()
        initObservers()
    }

    abstract fun initView()
    abstract fun initListeners()
    abstract fun initObservers()

    private fun initGenericBinding() {
        val inflateMethod = bindingClass.getBindingMethod()
        val inflater = LayoutInflater.from(this)
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        val isAttachToRoot = false
        binding = inflateMethod.invoke(null, inflater, rootView, isAttachToRoot) as VB
        setContentView(binding.root)
    }

    protected inline fun viewModel(action: VM.() -> Unit) {
        action(viewModel)
    }

    protected inline fun viewBinding(action: VB.() -> Unit) {
        action(binding)
    }
}
