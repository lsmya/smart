package cn.lsmya.smart

import cn.lsmya.smart.base.BaseViewModel
import java.lang.reflect.ParameterizedType

object VMUtils {
    fun <VM> getVMClass(mCls: Class<*>): Class<VM> {
        var cls: Class<*>? = mCls
        var vmClass: Class<VM>? = null
        while (vmClass == null && cls != null) {
            vmClass = getClass(cls) as Class<VM>?
            cls = cls.superclass
        }
        if (vmClass == null) {
            vmClass = BaseViewModel::class.java as Class<VM>
        }
        return vmClass
    }

    private fun getClass(cls: Class<*>): Class<*>? {
        val type = cls.genericSuperclass
        if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            for (t in types) {
                if (t is Class<*>) {
                    if (BaseViewModel::class.java.isAssignableFrom(t)) {
                        return t
                    }
                } else if (t is ParameterizedType) {
                    val rawType = t.rawType
                    if (rawType is Class<*>) {
                        if (BaseViewModel::class.java.isAssignableFrom(rawType)) {
                            return rawType
                        }
                    }
                }
            }
        }
        return null
    }
}