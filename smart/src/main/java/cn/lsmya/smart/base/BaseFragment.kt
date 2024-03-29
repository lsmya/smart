package cn.lsmya.smart.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.extension.postEvent
import cn.lsmya.smart.extension.request
import cn.lsmya.smart.model.ToastModel
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.net.SocketTimeoutException


abstract class BaseFragment<VB : ViewBinding> : Fragment(), BaseInterface {

    private var viewBinding: VB? = null

    private val inflateMethod: Method

    init {
        //获取泛型参数化类型信息
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        //由BaseViewBindingFragment<VB : ViewBinding>可知只有一个参数类型
        val clazz = parameterizedType.actualTypeArguments.first() as Class<*>
        //获取ViewBinding实现类中的inflate方法，如下：
        inflateMethod = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //获取viewBinding
        viewBinding = inflateMethod.invoke(null, inflater, container, false) as VB
        return viewBinding?.root
    }

    fun getBinding(): VB? {
        return viewBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private val loading by lazy {
        XPopup.Builder(requireContext())
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    /**
     * 显示加载loading动画
     */
    open fun showLoading() {
        if (!loading.isShow) {
            loading.show()
        }
    }

    /**
     * 隐藏加载loading动画
     */
    open fun hideLoading() {
        if (loading.isShow) {
            loading.dismiss()
        }
    }

    /**
     * 选择图片(单选)
     */
    protected fun selectSingleImage(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        if (requireActivity() is BaseActivity<*>)
            (requireActivity() as BaseActivity<*>).selectSingleImage(
                cancel = cancel,
                callback = callback
            )
    }

    /**
     * 选择视频(单选)
     */
    protected fun selectSingleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        if (requireActivity() is BaseActivity<*>)
            (requireActivity() as BaseActivity<*>)?.selectSingleVideo(
                cancel = cancel,
                callback = callback
            )
    }

    /**
     * 选择图片(多选)
     */
    protected fun selectMultipleImage(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        if (requireActivity() is BaseActivity<*>)
            (requireActivity() as BaseActivity<*>).selectMultipleImage(
                cancel = cancel,
                callback = callback
            )
    }

    /**
     * 选择视频(多选)
     */
    protected fun selectMultipleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        if (requireActivity() is BaseActivity<*>)
            (requireActivity() as BaseActivity<*>).selectMultipleVideo(
                cancel = cancel,
                callback = callback
            )
    }

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return launch(block = block, showToast = true)
    }

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null,
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

}