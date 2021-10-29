package cn.lsmya.smart.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * ARouter路由跳转
 */
fun navigation(
    url: String,
    activity: Activity? = null,
    requestCode: Int = -1,
    flags: Int = -1,
    block: (Postcard.() -> Unit)? = null
) {
    val postcard = ARouter.getInstance().build(url)
    postcard?.let {
        block?.let { it1 ->
            postcard.apply(it1)
        }
        if (flags != -1) {
            it.withFlags(flags)
        }
        if (activity == null) {
            postcard.navigation()
        } else {
            if (requestCode != -1) {
                postcard.navigation(activity, requestCode)
            } else {
                postcard.navigation()
            }
        }
    }
}


fun Context.openActivity(
    cls: Class<*>, block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, cls)
    block?.let {
        intent.apply(it)
    }
    startActivity(intent)
}

fun ComponentActivity.registerForActivityResult(callback: ActivityResultCallback<ActivityResult>) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)
