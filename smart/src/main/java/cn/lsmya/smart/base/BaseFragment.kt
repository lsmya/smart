package cn.lsmya.smart.base

import androidx.fragment.app.Fragment
import cn.lsmya.smart.extension.callback
import cn.lsmya.smart.utils.GlideEngine
import cn.lsmya.smart.utils.ImageFileCompressEngine
import cn.lsmya.smart.utils.ImageFileCropEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.lxj.xpopup.XPopup

abstract class BaseFragment : Fragment() {

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
        if (activity is BaseActivity)
            (activity as BaseActivity).selectSingleImage(
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
        if (activity is BaseActivity)
            (activity as BaseActivity).selectSingleVideo(
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
        if (activity is BaseActivity)
            (activity as BaseActivity).selectMultipleImage(
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
        if (activity is BaseActivity)
            (activity as BaseActivity).selectMultipleVideo(
                cancel = cancel,
                callback = callback
            )
    }
}