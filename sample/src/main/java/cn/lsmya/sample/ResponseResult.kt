package cn.lsmya.sample

import cn.lsmya.smart.base.BaseResponseParser
import com.google.gson.annotations.SerializedName

class ResponseResult<T>(
    @SerializedName("code")
    var errorCode: Int = -1,
    @SerializedName("msg")
    var errorMsg: String? = "",
    @SerializedName("data")
    var data: T? = null,
) : BaseResponseParser<T> {
    override fun isSuccess(): Boolean {
        return errorCode == 1
    }

    override fun getMessage(): String? {
        return errorMsg
    }

    override fun getResultCode(): Int {
        return errorCode
    }

    override fun getResult(): T? {
        return data
    }

}