package com.zerosword.data.services

import com.skydoves.sandwich.ApiResponse
import com.zerosword.data.network.response.KakaoImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String, //accuracy is default, recency is option
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80 // 한 페이지에 보여질 이미지 수, 기본값 80
    ): ApiResponse<KakaoImageResponse>

}