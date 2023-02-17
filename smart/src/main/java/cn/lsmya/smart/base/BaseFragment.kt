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
    protected fun pictureSelectorSingleOfImage(
        chooseMode: Int = SelectMimeType.ofImage(),
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        pictureSelectorOfImage(chooseMode = chooseMode, cancel = cancel, callback = {
            if (it.isNotEmpty()) {
                callback?.invoke(it[0])
            }
        })
    }

    /**
     * 选择图片（可多选）
     */
    protected fun pictureSelectorOfImage(
        chooseMode: Int = SelectMimeType.ofImage(),
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)? = null,
    ) {
        PictureSelector.create(this)
            .openGallery(chooseMode)
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setImageEngine(GlideEngine.createGlideEngine())
            .isDirectReturnSingle(true)
            .setCropEngine(ImageFileCropEngine())
            .setCompressEngine(ImageFileCompressEngine())
            .callback(cancel = cancel, callback = callback)
    }
}