package cn.lsmya.sample

import com.google.gson.annotations.SerializedName


data class NewTypesModel(
    @SerializedName("typeId")
    val typeId: Int = 0,
    @SerializedName("typeName")
    val typeName: String = "",
)