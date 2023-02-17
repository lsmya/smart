package cn.lsmya.smart.base

interface BaseResponseParser<T> {
    /**
     * 是否请求成功
     */
    fun isSuccess(): Boolean

    /**
     * 获取错误信息
     */
    fun getMessage(): String?

    /**
     * 获取服务器返回状态码
     */
    fun getResultCode(): Int

    fun getResult(): T?
}