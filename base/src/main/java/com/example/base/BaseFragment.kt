package com.example.cleanarchitecture.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewBinding>(
    @LayoutRes layout: Int,
    factoryBind: (View) -> T,
): Fragment(layout) {

    protected val binding: T by viewBinding(factoryBind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
        loadTask()
    }

    fun Fragment.runDefaultLaunch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch(dispatcher) {
            block()
        }
    }

    open protected fun setupListener(){}
    open protected fun setupObserver(){}
    open protected fun loadTask() {}

}