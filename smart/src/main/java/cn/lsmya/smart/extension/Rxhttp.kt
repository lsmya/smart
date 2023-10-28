package cn.lsmya.smart.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import cn.lsmya.smart.base.BaseViewModel
import cn.lsmya.smart.model.ToastModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, showToast = true)
}

fun BaseViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = { hideLoading() },
    onStart: (() -> Unit)? = { showLoading() },
    onFinally: (() -> Unit)? = { hideLoading() },
    showToast: Boolean = true
): Job {
    return request(
        block = block,
        onError = {
            if (showToast) {
                it.message?.let { msg ->
                    if (it is SocketTimeoutException) {
                        ToastModel("网络连接超时").postEvent()
                    } else {
                        ToastModel(msg).postEvent()
                    }
                }
            }
            onError?.invoke(it)
        },
        onStart =
        if (onStart == null) {
            showLoading()
            null
        } else {
            onStart
        },
        onFinally =
        if (onFinally == null) {
            hideLoading()
            null
        } else onFinally
    )
}


fun LifecycleOwner.request(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return lifecycleScope.launch {
        try {
            coroutineScope {
                onStart?.invoke()
                block()
            }
        } catch (e: Throwable) {
            if (onError != null && isActive) {
                try {
                    onError(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            } else {
                e.printStackTrace()
            }
        } finally {
            onFinally?.invoke()
        }
    }
}
fun ViewModel.request(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return viewModelScope.launch {
        try {
            coroutineScope {
                onStart?.invoke()
                block()
            }
        } catch (e: Throwable) {
            if (onError != null && isActive) {
                try {
                    onError(e)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            } else {
                e.printStackTrace()
            }
        } finally {
            onFinally?.invoke()
        }
    }
}