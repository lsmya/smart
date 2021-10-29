package cn.lsmya.smart.extension

import android.text.TextUtils
import com.luck.picture.lib.PictureSelectionModel
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.tools.SdkVersionUtils

fun PictureSelectionModel.callback(
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)? = null,
) {
    this.forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: MutableList<LocalMedia>?) {
            result?.let {
                val list = ArrayList<String>()
                it.forEach { localMedia ->
                    val path: String =
                        if (localMedia.isCut && !localMedia.isCompressed) {
                            // 裁剪过
                            localMedia.cutPath
                        } else if (localMedia.isCompressed || localMedia.isCut && localMedia.isCompressed) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            localMedia.compressPath
                        } else {
                            if (SdkVersionUtils.checkedAndroid_Q() && !TextUtils.isEmpty(localMedia.androidQToPath)) {
                                //大于等于Android_Q
                                localMedia.androidQToPath
                            } else {
                                localMedia.path
                            }
                        }
                    list.add(path)
                }
                callback?.invoke(list)
            }
        }

        override fun onCancel() {
            cancel?.invoke()
        }
    })
}