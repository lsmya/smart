package cn.lsmya.smart.model

import cn.lsmya.smart.base.BaseResponseParser
import com.google.gson.annotations.SerializedName

class ResponseResult<T>(
    @SerializedName("code")
    var errorCode: Int = -1,
    @SerializedName("msg")
    var errorMsg: String? = "",
    @SerializedName("data")
    var data: T? = null,
) : BaseResponseParser {
    override fun isSuccess(): Boolean {
        return errorCode == 1
    }

    override fun getMsg(): String? {
        return errorMsg
    }

    override fun getCode(): Int {
        return errorCode
    }
}