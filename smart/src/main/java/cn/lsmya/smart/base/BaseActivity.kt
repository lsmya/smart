package cn.lsmya.smart.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.lsmya.smart.extension.callback
import cn.lsmya.smart.utils.GlideEngine
import cn.lsmya.smart.utils.ImageFileCompressEngine
import cn.lsmya.smart.utils.ImageFileCropEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.lxj.xpopup.XPopup

abstract class BaseActivity : AppCompatActivity() {

    private val loading by lazy {
        XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
     * 解决软键盘与底部输入框冲突问题
     */
    protected open fun keyboardEnable(): Boolean {
        return true
    }

    /**
     * 是否开启亮色状态栏
     */
    protected open fun enableLightMode(): Boolean {
        return true
    }

    /**
     * 选择图片(单选)
     */
    fun selectSingleImage(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofImage(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = {
                if (it.isNotEmpty()) {
                    callback?.invoke(it[0])
                }
            })
    }

    /**
     * 选择视频(单选)
     */
    fun selectSingleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((String) -> Unit)? = null,
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofVideo(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = {
                if (it.isNotEmpty()) {
                    callback?.invoke(it[0])
                }
            })
    }

    /**
     * 选择图片(多选)
     */
    fun selectMultipleImage(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofImage(),
            selectionMode = SelectModeConfig.MULTIPLE,
            cancel = cancel,
            callback = callback,
        )
    }

    /**
     * 选择视频(多选)
     */
    fun selectMultipleVideo(
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)
    ) {
        pictureSelector(
            chooseMode = SelectMimeType.ofVideo(),
            selectionMode = SelectModeConfig.SINGLE,
            cancel = cancel,
            callback = callback
        )
    }

    /**
     * 选择图片、视频（可多选）
     */
    private fun pictureSelector(
        chooseMode: Int,
        selectionMode: Int,
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)? = null,
    ) {
        PictureSelector.create(this)
            .openGallery(chooseMode)
            .setSelectionMode(selectionMode)
            .setImageEngine(GlideEngine.createGlideEngine())
            .isDirectReturnSingle(true)
            .setCropEngine(ImageFileCropEngine())
            .setCompressEngine(ImageFileCompressEngine())
            .callback(cancel = cancel, callback = callback)
    }
}