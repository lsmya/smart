package cn.lsmya.smart.extension

import androidx.lifecycle.rxLifeScope
import cn.lsmya.smart.base.BaseActivity
import cn.lsmya.smart.base.BaseFragment
import cn.lsmya.smart.base.BaseViewModel
import cn.lsmya.smart.model.ToastModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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
    return rxLifeScope.launch(
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

fun BaseFragment.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, showToast = true)
}

fun BaseFragment.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
    showToast: Boolean = true
): Job {
    return rxLifeScope.launch(
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

fun BaseActivity.launch(
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch(block = block, showToast = true)
}

fun BaseActivity.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
    showToast: Boolean = true
): Job {
    return rxLifeScope.launch(
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