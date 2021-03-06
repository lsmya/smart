package cn.lsmya.smart.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.lsmya.smart.extension.callback
import cn.lsmya.smart.immersionbar.immersionBar
import cn.lsmya.smart.utils.GlideEngine
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.lxj.xpopup.XPopup


open class BaseActivity : AppCompatActivity() {

    private val loading by lazy {
        XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .asLoading()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableTranslucent()) {
            immersionBar {
                navigationBarColor("#00000000")
                statusBarDarkFont(enableLightMode())
                keyboardEnable(keyboardEnable())  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            }
        }
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
     * 是否需要沉浸式
     */
    protected open fun enableTranslucent(): Boolean {
        return true
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
    protected fun pictureSelectorSingleOfImage(
        chooseMode: Int = PictureMimeType.ofImage(),
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
        chooseMode: Int = PictureMimeType.ofImage(),
        cancel: (() -> Unit)? = null,
        callback: ((ArrayList<String>) -> Unit)? = null,
    ) {
        PictureSelector.create(this)
            .openGallery(chooseMode)
            .selectionMode(PictureConfig.SINGLE)
            .imageEngine(GlideEngine.createGlideEngine())
            .isSingleDirectReturn(true)
            .isEnableCrop(true)
            .isCompress(true)
            .callback(cancel = cancel, callback = callback)
    }
}