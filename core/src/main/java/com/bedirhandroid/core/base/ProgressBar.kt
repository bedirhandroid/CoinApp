package com.bedirhandroid.core.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bedirhandroid.core.R

private const val TAG = "ProgressBar"

// Progress Bar for BaseFragment
class ProgressBar : DialogFragment() {

    private var isShown: Boolean = false

    fun isProgressShown(): Boolean = isShown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.fullScreenDialog)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.progress, container, false)
    }

    fun show(manager: FragmentManager) {
        if (!isShown && manager.findFragmentByTag(TAG) == null) {
            manager.beginTransaction().add(this, TAG).commitAllowingStateLoss()
            isShown = true
        }
    }

    fun hide() {
        if (isShown) {
            dismissAllowingStateLoss()
            isShown = false
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShown = false
    }
}
