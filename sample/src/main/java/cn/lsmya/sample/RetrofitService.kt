package cn.lsmya.sample

import cn.lsmya.smart.model.ResponseResult
import retrofit2.http.GET

interface RetrofitService {
    @GET("/api/news/types?app_id=jql2tostkstksoej&app_secret=ZHBMakcwVEtBNFBEWEE0RlQ2amZidz09")
    suspend fun getNews(): ResponseResult<ArrayList<NewTypesModel>>
}