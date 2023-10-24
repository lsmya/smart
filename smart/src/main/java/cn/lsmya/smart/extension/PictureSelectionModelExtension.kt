package cn.lsmya.smart.extension

import android.content.Context
import android.net.Uri
import cn.lsmya.smart.utils.ImageUriPathUtil
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.DateUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.File

fun PictureSelectionModel.callback(
    context: Context,
    cancel: (() -> Unit)? = null,
    callback: ((ArrayList<String>) -> Unit)? = null,
) {
    this.forResult(object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>?) {
            result?.let {
                val list = ArrayList<String>()
                it.forEach { localMedia ->
                    val filePath: String = localMedia.availablePath
                    if (PictureMimeType.isContent(filePath)) {
                        if (localMedia.isCut || localMedia.isCompressed) {
                            list.add(filePath)
                        } else {
                            val uri = Uri.parse(filePath)
                            val path =
                                ImageUriPathUtil.getPathFromUri(context, uri)
                            list.add(path)
                        }
                    } else {
                        list.add(filePath)
                    }
                }
                callback?.invoke(list)
            }
        }

        override fun onCancel() {
            cancel?.invoke()
        }

    })
}

/**
 * 自定义压缩
 */
class ImageFileCompressEngine : CompressFileEngine {
    override fun onStartCompress(
        context: Context,
        source: java.util.ArrayList<Uri>,
        call: OnKeyValueResultCallbackListener?
    ) {
        Luban.with(context).load(source).ignoreBy(100).setRenameListener { filePath ->
            val indexOf = filePath.lastIndexOf(".")
            val postfix = if (indexOf != -1) filePath.substring(indexOf) else ".jpg"
            DateUtils.getCreateFileName("CMP_") + postfix
        }.setCompressListener(object : OnNewCompressListener {
            override fun onStart() {}
            override fun onSuccess(source: String, compressFile: File) {
                call?.onCallback(source, compressFile.absolutePath)
            }

            override fun onError(source: String, e: Throwable) {
                call?.onCallback(source, null)
            }
        }).launch()
    }
}