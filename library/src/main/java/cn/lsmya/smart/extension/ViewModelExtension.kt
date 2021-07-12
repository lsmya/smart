package cn.lsmya.smart.extension

import androidx.lifecycle.viewModelScope
import cn.lsmya.smart.model.Error
import cn.lsmya.smart.base.BaseResponseParser
import cn.lsmya.smart.base.BaseViewModel
import com.bumptech.glide.load.HttpException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @param request       请求内容
 * @param showToast     当发生错误时是否自动显示Toast
 * @param showLoading   请求开始前是否显示loading弹窗
 * @param onSuccess     请求成功回调
 */
fun <T : BaseResponseParser> BaseViewModel.launch(
    request: suspend CoroutineScope.() -> T,
    showToast: Boolean = true,
    showLoading: Boolean = true,
    onSuccess: ((T) -> Unit),
) {
    launch(
        request = request,
        showToast = showToast,
        showLoading = showLoading,
        onStart = null,
        onSuccess = onSuccess,
        onError = null,
        onFinally = null)
}

/**
 * @param request       请求内容
 * @param showToast     当发生错误时是否自动显示Toast
 * @param showLoading   请求开始前是否显示loading弹窗
 * @param onStart       请求前回调
 * @param onSuccess     请求成功回调
 * @param onError       请求错误回调：包含服务器返回的错误及请求错误
 * @param onFinally     请求结束回调
 */
fun <T : BaseResponseParser> BaseViewModel.launch(
    request: suspend CoroutineScope.() -> T,
    showToast: Boolean = true,
    showLoading: Boolean = true,
    onStart: (() -> Unit)? = null,
    onSuccess: ((T) -> Unit),
    onError: ((Error) -> Unit)? = null,
    onFinally: (() -> Unit)? = null,
): Job {
    return viewModelScope.launch {
        try {
            if (showLoading) {
                showLoading()
            }
            onStart?.invoke()
            val response = request()
            if (response.isSuccess()) {
                onSuccess.invoke(response)
            } else {
                val error = Error(code = response.getCode(), msg = response.getMsg())
                if (showToast) {
                    error.msg?.let {
                        toast(it)
                    }
                }
                if (onError != null && isActive) {
                    onError(error)
                }
            }
        } catch (e: Exception) {
            if (onError != null && isActive) {
                try {
                    val error = build(e)
                    if (showToast) {
                        error.msg?.let {
                            toast(it)
                        }
                    }
                    onError(error)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            } else {
                e.printStackTrace()
            }
        } finally {
            if (showLoading) {
                hideLoading()
            }
            onFinally?.invoke()
        }
    }
}

/**
 * 网络异常、网络连接失败，请检查后再试
 */
const val CODE_NET_ERROR = 4000

/**
 * 请求超时，请稍后再试
 */
const val CODE_TIMEOUT = 4080

/**
 * 数据解析错误
 */
const val CODE_JSON_PARSE_ERROR = 4010

/**
 * 系统错误
 */
const val CODE_SERVER_ERROR = 5000
private fun build(e: Throwable): Error {
    //网络状态码
    return if (e is HttpException) {
        Error(CODE_NET_ERROR, "网络异常(${e.statusCode},${e.message})")
    } else if (e is UnknownHostException) {
        Error(CODE_NET_ERROR, "网络连接失败，请检查后再试")
    } else if (e is ConnectTimeoutException || e is SocketTimeoutException) {
        Error(CODE_TIMEOUT, "请求超时，请稍后再试")
    } else if (e is JsonParseException || e is JSONException || e is MalformedJsonException) {
        // Json解析失败
        Error(code = CODE_JSON_PARSE_ERROR, msg = "数据解析错误，请稍后再试")
    } else if (e is IOException) {
        Error(CODE_NET_ERROR, "网络异常(${e.message})")
    } else {
        Error(CODE_SERVER_ERROR, "系统错误(${e.message})")
    }
}
