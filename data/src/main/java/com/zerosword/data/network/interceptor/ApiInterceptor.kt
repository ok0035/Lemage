package com.zerosword.data.network.interceptor

import com.zerosword.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            val newRequest = request()
                    .newBuilder()
                    .addHeader(
                        name = "Authorization",
                        value = "KakaoAK ${BuildConfig.kakaoApiKey}"
                    ).build()

            chain.proceed(newRequest)
        }
    }
}