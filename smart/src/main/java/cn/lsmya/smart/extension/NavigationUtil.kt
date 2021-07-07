package cn.lsmya.smart.extension
//
//import android.app.Activity
//import com.alibaba.android.arouter.facade.Postcard
//import com.alibaba.android.arouter.launcher.ARouter
//
///**
// * ARouter路由跳转
// */
//fun navigation(
//    url: String,
//    activity: Activity? = null,
//    requestCode: Int = -1,
//    flags: Int = -1,
//    block: (Postcard.() -> Unit)? = null
//) {
//    val postcard = ARouter.getInstance().build(url)
//    postcard?.let {
//        block?.let { it1 ->
//            postcard.apply(it1)
//        }
//        if (flags != -1) {
//            it.withFlags(flags)
//        }
//        if (activity == null) {
//            postcard.navigation()
//        } else {
//            if (requestCode != -1) {
//                postcard.navigation(activity, requestCode)
//            } else {
//                postcard.navigation()
//            }
//        }
//    }
//}