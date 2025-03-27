package com.bedirhandroid.core.base

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.bedirhandroid.core.util.getBindingMethod
import com.bedirhandroid.core.util.getViewModelByLazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : HiltFragment() {

    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set

    private val type = javaClass.genericSuperclass as ParameterizedType
    private val bindingClass = type.actualTypeArguments[0] as Class<VB>
    private val viewModelClass = type.actualTypeArguments[1] as Class<VM>

    private val progressBar: ProgressBar by lazy { ProgressBar() }

    abstract fun initView()
    abstract fun initListeners()
    abstract fun initObservers()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        binding = bindingClass.getBindingMethod().invoke(null, inflater, container, false) as VB
        viewModel = viewModelClass.getViewModelByLazy(this)

        initView()
        initListeners()
        initObservers()
        observeBaseUiState()

        return binding.root
    }

    /**
     * **Tüm Fragment’lerde global UI state’i yönet**
     */
    private fun observeBaseUiState() {
        collectBaseFlow(viewModel.loadingState) { isLoading ->
            handleLoadingState(isLoading)
        }

        collectBaseFlow(viewModel.errorState) { error ->
            error?.let { showError(it) }
        }
    }

    /**
     * **Hata mesajını gösterme (Fragment özelinde override edilebilir)**
     */
    protected open fun showError(message: String?) {
        message?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * **Loading durumunu yönetme (Fragment özelinde override edilebilir)**
     */
    protected open fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            if (!progressBar.isAdded) {
                progressBar.show(requireActivity().supportFragmentManager, "progress")
            }
        } else {
            if (progressBar.isAdded) {
                progressBar.dismiss()
            }
        }
    }

    /**
     * **Tüm StateFlow ve SharedFlow türleri için genel veri toplama fonksiyonu**
     */
    protected fun <T> collectFlow(
        flow: Flow<T>,
        onSuccess: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { onSuccess(it) }
            }
        }
    }

    /**
     * **Sadece BaseViewModel'deki global UI state'leri dinlemek için**
     */
    private fun <T> collectBaseFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { onCollect(it) }
            }
        }
    }
}
