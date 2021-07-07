package cn.lsmya.smart.base

interface BaseResponseParser {
    /**
     * 是否请求成功
     */
    fun isSuccess(): Boolean

    /**
     * 获取错误信息
     */
    fun getMsg(): String?

    /**
     * 获取服务器返回状态码
     */
    fun getCode(): Int
}