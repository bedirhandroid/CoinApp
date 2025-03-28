package com.bedirhandroid.network.interceptor

import android.util.Log
import com.bedirhandroid.network.BuildConfig
import com.bedirhandroid.network.util.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("qweqwe", "intercept: ${chain.request()}")
        val request = chain.request().newBuilder()
            .addHeader(NetworkConstants.HEADER_X_ACCESS_TOKEN, BuildConfig.API_KEY)
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}